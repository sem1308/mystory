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
import uos.mystory.exception.DuplicateException;
import uos.mystory.exception.LimitExceededException;
import uos.mystory.exception.ResourceNotFoundException;
import uos.mystory.exception.massage.MessageManager;
import uos.mystory.repository.BlogRepository;
import uos.mystory.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class BlogService {
    private final BlogRepository blogRepository;
    private final UserRepository userRepository;

    /**
     * @param blogDTO
     * @return 블로그 번호
     * @title 블로그 생성
     */
    public Long saveBlog(@NotNull InsertBlogDTO blogDTO) {
        // url 중복 체크
        validateUrlDuplication(blogDTO.getUrl());
        // 유저 최대 블로그 개수 체크
        validateNumBlogExceeded(blogDTO.getUser());

        // entity 생성
        Blog blog = Blog.create(blogDTO.getName(), blogDTO.getUrl(), blogDTO.getDescription(), blogDTO.getUser());

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
     * @param blogDTO
     * @return
     * @title 블로그 정보 변경
     */
    public void updateBlog(@NotNull UpdateBlogDTO blogDTO) {
        Blog blog = getBlog(blogDTO.getId());
        blog.update(blogDTO.getName(), blogDTO.getUrl(), blogDTO.getDescription());
    }

    /**
     * @param id
     * @return 블로그 엔티티
     * @title 블로그 번호로 블로그 엔티티 가져오기
     */
    public Blog getBlog(Long id) {
        return blogRepository.findById(id).orElseThrow(()->new ResourceNotFoundException(MessageManager.getMessage("error.notfound.blog")));
    }

    /**
     * @param pageable
     * @return 블로그 엔티티 페이징 리스트
     * @title 블로그 엔티티 목록 가져오기
     */
    public Page<Blog> getBlogs(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

}
