package uos.mystory.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import uos.mystory.domain.Blog;
import uos.mystory.domain.Category;
import uos.mystory.domain.Post;
import uos.mystory.domain.User;
import uos.mystory.domain.enums.OpenState;
import uos.mystory.domain.enums.PostType;
import uos.mystory.domain.enums.WriteType;
import uos.mystory.dto.mapping.insert.InsertBlogDTO;
import uos.mystory.dto.mapping.insert.InsertCategoryDTO;
import uos.mystory.dto.mapping.insert.InsertPostDTO;
import uos.mystory.dto.mapping.insert.InsertUserDTO;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PostServiceTest {
    @Autowired
    private PostService postService;
    @Autowired
    private BlogService blogService;
    @Autowired
    private UserService userService;
    @Autowired
    private CategoryService categoryService;

    private User user;
    private Blog blog;
    private Category category;

    @BeforeEach
    public void setup() {
        Long userId = userService.saveUser(new InsertUserDTO("sem1308", "1308", "ddory", "01000000000"));
        this.user = userService.getUser(userId);
        Long blogId = blogService.saveBlog(new InsertBlogDTO("Dev", "https://han-dev.mystory.com", "기본 블로그", user));
        this.blog = blogService.getBlog(blogId);
        Long categoryId = categoryService.saveCategory(new InsertCategoryDTO("BackEnd", blog));
        this.category = categoryService.getCategory(categoryId);
    }

    @Test
    public void 게시글_생성() throws Exception {
        //given
        String title = "첫 게시글";
        String url = blog.getUrl()+"/"+title.replace(" ", "-");
        InsertPostDTO postDTO = new InsertPostDTO(PostType.POST, title, "그냥 끄적이는 글", WriteType.BASIC, OpenState.CLOSE, url, null, user, category, blog);

        //when
        Long id = postService.savePost(postDTO);
        Post post = postService.getPost(id);

        //then
        assertEquals(post.getId(),id);
        System.out.println(post);
    }


}