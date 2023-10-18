package uos.mystory.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import uos.mystory.domain.Blog;
import uos.mystory.domain.Category;
import uos.mystory.domain.User;
import uos.mystory.dto.mapping.insert.InsertBlogDTO;
import uos.mystory.dto.mapping.insert.InsertCategoryDTO;
import uos.mystory.dto.mapping.insert.InsertUserDTO;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CategoryServiceTest {
    @Autowired
    BlogService blogService;
    @Autowired
    UserService userService;
    @Autowired
    CategoryService categoryService;

    User user;
    Blog blog;

    @BeforeEach
    public void setup() {
        Long id = userService.saveUser(new InsertUserDTO("sem1308", "1308", "ddory", "01000000000"));
        this.user = userService.getUser(id);
        Long blogId = blogService.saveBlog(new InsertBlogDTO("Dev", "https://han-dev.mystory.com", "기본 블로그", user));
        this.blog = blogService.getBlog(blogId);
    }

    @Test
    public void 카테고리_생성() throws Exception {
        //given
        InsertCategoryDTO categoryDTO = new InsertCategoryDTO("BackEnd", blog);

        //when
        Long id = categoryService.saveCategory(categoryDTO);
        Category category = categoryService.getCategory(id);

        //then
        assertEquals(category.getId(), id);

    }

}