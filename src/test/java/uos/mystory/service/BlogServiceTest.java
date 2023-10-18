package uos.mystory.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import uos.mystory.domain.Blog;
import uos.mystory.domain.User;
import uos.mystory.dto.mapping.insert.InsertBlogDTO;
import uos.mystory.dto.mapping.insert.InsertUserDTO;
import uos.mystory.dto.mapping.update.UpdateBlogDTO;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class BlogServiceTest {
    @Autowired
    BlogService blogService;
    @Autowired
    UserService userService;
    private User user;

    @BeforeEach
    public void setup() {
        Long id = userService.saveUser(new InsertUserDTO("sem1308", "1308", "ddory", "01000000000"));
        this.user = userService.getUser(id);
    }

    @Test
    public void 블로그_생성() throws Exception {
        //given
        InsertBlogDTO insertBlogDTO = new InsertBlogDTO("Dev", "https://han-dev.mystory.com", "기본 블로그", user);

        //when
        Long id = blogService.saveBlog(insertBlogDTO);
        Blog blog = blogService.getBlog(id);

        //then
        assertEquals(blog.getId(),id);
        assertEquals(blog.getUser(),user);
        System.out.println(blog);
    }
    
    @Test
    public void 블로그_변경() throws Exception {
        //given
        InsertBlogDTO insertBlogDTO = new InsertBlogDTO("Dev", "https://han-dev.mystory.com", "기본 블로그", user);
        Long id = blogService.saveBlog(insertBlogDTO);

        String updatedName = "Han-Dev";
        String updatedDesc = "상향된 블로그";
        String updatedUrl = null;
        UpdateBlogDTO updateBlogDTO = new UpdateBlogDTO(id, updatedName, updatedUrl, updatedDesc);

        //when
        blogService.updateBlog(updateBlogDTO);

        //then
        Blog blog = blogService.getBlog(id);
        assertEquals(blog.getName(), updatedName);
        assertEquals(blog.getDescription(), updatedDesc);
        System.out.println(blog);
    }
}
