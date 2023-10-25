package uos.mystory.service;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uos.mystory.domain.Blog;
import uos.mystory.domain.Post;
import uos.mystory.domain.User;
import uos.mystory.dto.mapping.insert.InsertPostDTO;
import uos.mystory.dto.mapping.update.UpdatePostDTO;
import uos.mystory.exception.DuplicateException;
import uos.mystory.exception.MismatchException;
import uos.mystory.exception.ResourceNotFoundException;
import uos.mystory.exception.massage.MessageManager;
import uos.mystory.repository.PostRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {
    private final PostRepository postRepository;

    /**
     * @title 게시글 등록
     * @param insertPostDTO
     * @return 게시글 번호
     */
    @Transactional(readOnly = false)
    public Long savePost(@NotNull InsertPostDTO insertPostDTO) {
        // url 중복 체크
        validateUrlDuplication(insertPostDTO.getUrl());
        // 글 쓴 user가 blog를 생성한 user인지 체크
        validateBlogOwner(insertPostDTO.getBlog(), insertPostDTO.getUser());

        Post post = Post.create(insertPostDTO);

        return postRepository.save(post).getId();
    }

    private void validateBlogOwner(@NotNull Blog blog,@NotNull User user) {
        if (!blog.getUser().equals(user)) {
            throw new MismatchException(MessageManager.getMessage("error.mismatch.blog.user"));
        }
    }

    private void validateUrlDuplication(String url) {
        Long numOfPost = postRepository.countByUrl(url);
        if (numOfPost != 0) {
            throw new DuplicateException(MessageManager.getMessage("error.duplicate.url"));
        }
    }

    /**
     * @title 게시글 변경
     * @param updatePostDTO
     * @return
     */
    @Transactional(readOnly = false)
    public void updatePost(@NotNull UpdatePostDTO updatePostDTO) {
        Post post = getPost(updatePostDTO.getId());
        post.update(updatePostDTO);
    }

    /**
     * @title 게시글 번호로 게시글 가져오기
     * @param id
     * @return 게시글 엔티티
     */
    public Post getPost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(MessageManager.getMessage("error.notfound.post")));
        // 조회수 1 증가
        post.addVisits();
        return post;
    }

}
