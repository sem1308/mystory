package uos.mystory.service;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import uos.mystory.domain.Blog;
import uos.mystory.domain.Post;
import uos.mystory.domain.User;
import uos.mystory.dto.mapping.insert.InsertPostDTO;
import uos.mystory.dto.mapping.update.UpdatePostDTO;
import uos.mystory.exception.DuplicateException;
import uos.mystory.exception.ResourceNotFoundException;
import uos.mystory.exception.massage.MessageManager;
import uos.mystory.repository.PostRepository;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    /**
     * @param postDTO
     * @return 게시글 번호
     * @title 게시글 등록
     */
    public Long savePost(@NotNull InsertPostDTO postDTO) {
        // url 중복 체크
        validateUrlDuplication(postDTO.getUrl());
        // 글 쓴 user가 blog를 생성한 user인지 체크
        validateBlogOwner(postDTO.getBlog(), postDTO.getUser());

        Post post = Post.create(postDTO.getPostType(), postDTO.getTitle(), postDTO.getContent(), postDTO.getWriteType(), postDTO.getOpenState()
                    , postDTO.getUrl(), postDTO.getTitleImgPath(), postDTO.getUser(), postDTO.getCategory(), postDTO.getBlog());

        return postRepository.save(post).getId();
    }

    private void validateBlogOwner(Blog blog, User user) {
        if (!blog.getUser().equals(user)) {
            throw new DuplicateException(MessageManager.getMessage("error.duplicate.url"));
        }
    }

    private void validateUrlDuplication(String url) {
        Long numOfPost = postRepository.countByUrl(url);
        if (numOfPost != 0) {
            throw new DuplicateException(MessageManager.getMessage("error.duplicate.url"));
        }
    }

    /**
     * @param postDTO
     * @return 게시글 번호
     * @title 게시글 등록
     */
    public void updatePost(@NotNull UpdatePostDTO postDTO) {
        Post post = getPost(postDTO.getId());
        post.update(postDTO.getPostType(),postDTO.getTitle(),postDTO.getContent(),postDTO.getWriteType()
                    ,postDTO.getOpenState(),postDTO.getTitleImgPath(),postDTO.getCategory());
    }

    /**
     * @param id
     * @return 게시글 번호
     * @title 게시글 등록
     */
    public Post getPost(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(MessageManager.getMessage("error.notfound.post")));
    }

}
