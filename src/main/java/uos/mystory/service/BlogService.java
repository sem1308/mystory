package uos.mystory.service;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uos.mystory.domain.Blog;
import uos.mystory.domain.User;
import uos.mystory.domain.enums.VisitedPath;
import uos.mystory.domain.history.BlogHistory;
import uos.mystory.dto.mapping.insert.InsertBlogDTO;
import uos.mystory.dto.mapping.insert.InsertBlogHistoryDTO;
import uos.mystory.dto.mapping.update.UpdateBlogDTO;
import uos.mystory.dto.mapping.select.SelectBlogInfoDTO;
import uos.mystory.exception.DuplicateException;
import uos.mystory.exception.LimitExceededException;
import uos.mystory.exception.ResourceNotFoundException;
import uos.mystory.exception.massage.MessageManager;
import uos.mystory.repository.BlogHistoryRepository;
import uos.mystory.repository.BlogRepository;
import uos.mystory.repository.condition.BlogSearchCondition;
import uos.mystory.repository.querydsl.BlogQueryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BlogService {
    private final BlogRepository blogRepository;
    private final BlogQueryRepository blogQueryRepository;
    private final BlogHistoryRepository blogHistoryRepository;

    /**
     * @title 블로그 생성
     * @param insertBlogDTO
     * @return 블로그 번호
     */
    public Long saveBlog(@NotNull InsertBlogDTO insertBlogDTO) {
        // url 중복 체크
        validateUrlDuplication(insertBlogDTO.getUrl());
        // 유저 최대 블로그 개수 체크
        validateNumBlogExceeded(insertBlogDTO.getUser());

        // entity 생성
        Blog blog = Blog.create(insertBlogDTO);

        return blogRepository.save(blog).getId();
    }

    private void validateNumBlogExceeded(@NotNull User user){
        if(user.getMaxNumBlog() <= blogRepository.countByUser(user)){
            throw new LimitExceededException(MessageManager.getMessage("error.exceeded.user.max_num_blog"));
        }
    }

    private void validateUrlDuplication(String url) {
        Long numOfBlog = blogRepository.countByUrl(url);
        if (numOfBlog != 0) {
            throw new DuplicateException(MessageManager.getMessage("error.duplicate.url"));
        }
    }

    /**
     * @title 블로그 정보 변경
     * @param updateBlogDTO
     * @return
     */
    public void updateBlog(@NotNull UpdateBlogDTO updateBlogDTO) {
        Blog blog = getBlog(updateBlogDTO.getId());
        blog.update(updateBlogDTO);
    }

    /**
     * @title 블로그 번호로 블로그 엔티티 가져오기
     * @param id
     * @return 블로그 엔티티
     */
    @Transactional(readOnly = true)
    public Blog getBlog(Long id) {
        return blogRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(MessageManager.getMessage("error.notfound.blog")));
    }

    /**
     * @title 블로그 번호로 블로그 엔티티 가져오기 (블로그를 방문 한 경우)
     * @param id
     * @return 블로그 엔티티
     */
    public Blog getBlogWhenVisit(Long id, VisitedPath path) {
        Blog blog = getBlog(id);
        // 블로그 방문수 증가
        blog.addVisits();
        // 블로그 방문 이력 생성
        blogHistoryRepository.save(BlogHistory.create(InsertBlogHistoryDTO.builder().blog(blog).path(path).build()));
        return blog;
    }

    /**
     * @title 블로그 엔티티 목록 가져오기
     * @param pageable
     * @return 블로그 엔티티 페이징 리스트
     */
    //TODO: Blog to BlogInfoDTO 교체
    @Transactional(readOnly = true)
    public Page<Blog> getBlogs(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

    /**
     * @title 조건에 따른 블로그 정보 가져오기
     * @param blogSearchCondition
     * @param pageable
     * @return
     */
    @Transactional(readOnly = true)
    public Page<SelectBlogInfoDTO> getBlogsByContidion(BlogSearchCondition blogSearchCondition, Pageable pageable) {
        return blogQueryRepository.findAll(blogSearchCondition, pageable);
    }

    @Transactional(readOnly = true)
    public List<SelectBlogInfoDTO> getBlogsByContidion(BlogSearchCondition blogSearchCondition) {
        return blogQueryRepository.findAll(blogSearchCondition);
    }

    /**
     * @title 블로그 번호로 블로그 삭제
     * @param blogId
     */
    public void deleteBlog(Long blogId) {
        List<BlogHistory> blogHistories = blogHistoryRepository.findAllByBlogId(blogId);
        blogHistoryRepository.deleteAll(blogHistories);
        blogRepository.deleteById(blogId);
    }
}
