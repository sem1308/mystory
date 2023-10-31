package uos.mystory.service;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uos.mystory.domain.Blog;
import uos.mystory.domain.Post;
import uos.mystory.domain.User;
import uos.mystory.domain.enums.VisitedPath;
import uos.mystory.domain.history.PostHistory;
import uos.mystory.dto.mapping.insert.InsertPostDTO;
import uos.mystory.dto.mapping.insert.InsertPostHistoryDTO;
import uos.mystory.dto.mapping.update.UpdatePostDTO;
import uos.mystory.exception.DuplicateException;
import uos.mystory.exception.MismatchException;
import uos.mystory.exception.ResourceNotFoundException;
import uos.mystory.exception.massage.MessageManager;
import uos.mystory.repository.PostHistoryRepository;
import uos.mystory.repository.PostRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {
    private final PostRepository postRepository;
    private final PostHistoryRepository postHistoryRepository;

    /**
     * @title 게시글 등록
     * @param insertPostDTO
     * @return 게시글 번호
     */
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
    public void updatePost(@NotNull UpdatePostDTO updatePostDTO) {
        Post post = getPost(updatePostDTO.getId());
        post.update(updatePostDTO);
    }

    /**
     * @title 게시글 번호로 게시글 가져오기
     * @param id
     * @return 게시글 엔티티
     */
    @Transactional(readOnly = true)
    public Post getPost(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(MessageManager.getMessage("error.notfound.post")));
    }

    /**
     * @title 게시글 번호로 게시글 엔티티 가져오기 (게시글을 방문 한 경우)
     * @param id
     * @return 게시글 엔티티
     */
    public Post getPostWhenVisit(Long id, VisitedPath path) {
        Post post = getPost(id);
        // 게시글 방문수 증가
        post.addVisits();
        // 게시글 방문 이력 생성
        postHistoryRepository.save(PostHistory.create(InsertPostHistoryDTO.builder().post(post).path(path).build()));
        return post;
    }

    /**
     * @title 게시글 번호로 게시글 삭제하기
     * @param id
     */
    public void deletePost(Long id) {
        List<PostHistory> postHistories = postHistoryRepository.findAllByPostId(id);
        postHistoryRepository.deleteAll(postHistories);
        postRepository.deleteById(id);
    }
}
