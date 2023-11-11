package uos.mystory.service;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uos.mystory.domain.Post;
import uos.mystory.domain.enums.VisitedPath;
import uos.mystory.domain.history.PostHistory;
import uos.mystory.dto.mapping.insert.InsertPostDTO;
import uos.mystory.dto.mapping.insert.InsertPostHistoryDTO;
import uos.mystory.dto.mapping.select.SelectPostInfoDTO;
import uos.mystory.dto.mapping.update.UpdatePostDTO;
import uos.mystory.dto.response.ResponsePostDTO;
import uos.mystory.exception.DuplicateException;
import uos.mystory.exception.ResourceNotFoundException;
import uos.mystory.exception.massage.MessageManager;
import uos.mystory.repository.PostHistoryRepository;
import uos.mystory.repository.PostRepository;
import uos.mystory.repository.condition.PostSearchCondition;
import uos.mystory.repository.querydsl.PostQueryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {
    private final PostRepository postRepository;
    private final PostHistoryRepository postHistoryRepository;
    private final PostQueryRepository postQueryRepository;

    /**
     * @title 게시글 등록
     * @param insertPostDTO
     * @return 게시글 번호
     */
    public Long savePost(@NotNull InsertPostDTO insertPostDTO) {
        // url 중복 체크
        validateUrlDuplication(insertPostDTO.getUrl());
        Post post = Post.create(insertPostDTO);

        // 글쓴이와 블로그 생성자 일치 확인
        post.getBlog().validateOwner(insertPostDTO.getUser());

        return postRepository.save(post).getId();
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

    @Transactional(readOnly = true)
    public SelectPostInfoDTO getPostInfo(Long id) {
        return postQueryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(MessageManager.getMessage("error.notfound.post")));
    }


    /**
     * @Title 전체 게시글 목록 가져오기
     * @return 게시글 목록
     */
    @Transactional(readOnly = true)
    public List<Post> getPosts() {
        return postRepository.findAll();
    }

    /**
     * @title 게시글 번호로 게시글 엔티티 가져오기 (게시글을 방문 한 경우)
     * @param id
     * @return 게시글 엔티티
     */
    public ResponsePostDTO getPostWhenVisit(Long id, VisitedPath path) {
        Post post = getPost(id);
        // 게시글 방문수 증가
        post.addVisits();
        // 게시글 방문 이력 생성
        postHistoryRepository.save(PostHistory.create(InsertPostHistoryDTO.builder().post(post).path(path).build()));
        return ResponsePostDTO.of(post);
    }

    /**
     * @title 조건에 따른 게시글 목록 페이지 가져오기
     * @param postSearchCondition
     * @param pageable
     * @return 게시글 목록 페이지
     */
    @Transactional(readOnly = true)
    public Page<SelectPostInfoDTO> getPostsByContidion(PostSearchCondition postSearchCondition, Pageable pageable) {
        return postQueryRepository.findAll(postSearchCondition, pageable);
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
