package uos.mystory.service;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uos.mystory.domain.Blog;
import uos.mystory.domain.User;
import uos.mystory.dto.mapping.insert.InsertBlogDTO;
import uos.mystory.dto.mapping.update.UpdateBlogDTO;
import uos.mystory.dto.response.BlogInfoDTO;
import uos.mystory.exception.DuplicateException;
import uos.mystory.exception.LimitExceededException;
import uos.mystory.exception.ResourceNotFoundException;
import uos.mystory.exception.massage.MessageManager;
import uos.mystory.repository.BlogRepository;
import uos.mystory.repository.condition.BlogSearchCondition;
import uos.mystory.repository.querydsl.BlogQueryRepository;

@Service
@RequiredArgsConstructor
public class BlogService {
    private final BlogRepository blogRepository;
    private final BlogQueryRepository blogQueryRepository;

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
    public Blog getBlog(Long id) {
        Blog blog = blogRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(MessageManager.getMessage("error.notfound.blog")));
        blog.addVisits();
        return blog;
    }

    /**
     * @title 블로그 엔티티 목록 가져오기
     * @param pageable
     * @return 블로그 엔티티 페이징 리스트
     */
    //TODO: Blog to BlogInfoDTO 교체
    public Page<Blog> getBlogs(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

    public Page<BlogInfoDTO> getBlogsByContidion(BlogSearchCondition blogSearchCondition, Pageable pageable) {
        return blogQueryRepository.findAll(blogSearchCondition, pageable);
    }

}
