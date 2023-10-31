package uos.mystory.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import uos.mystory.domain.Blog;
import uos.mystory.domain.Category;
import uos.mystory.domain.User;
import uos.mystory.dto.mapping.insert.InsertBlogDTO;
import uos.mystory.dto.mapping.insert.InsertCategoryDTO;
import uos.mystory.dto.mapping.insert.InsertUserDTO;
import uos.mystory.dto.mapping.update.UpdateCategoryDTO;
import uos.mystory.exception.ResourceNotFoundException;
import uos.mystory.repository.CategoryRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CategoryServiceTest{
    @Autowired
    CategoryService categoryService;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    UserService userService;

    @Autowired
    BlogService blogService;


    User user;
    Blog blog;

    @BeforeEach
    public void setup() {
        Long id = userService.saveUser(InsertUserDTO.builder().userId("sem1308").userPw("1308").nickname("ddory").phoneNum("01000000000").build());
        this.user = userService.getUser(id);
        Long blogId = blogService.saveBlog(InsertBlogDTO.builder().name("Dev").url("https://han-dev.mystory.com").description("기본 블로그").user(user).build());
        this.blog = blogService.getBlog(blogId);
    }

    @AfterEach
    public void clear() {
        categoryRepository.deleteAll();
        blogService.deleteBlog(blog.getId());
        userService.deleteUser(user.getId());
    }

    @Test
    protected void 카테고리_생성() throws Exception {
        //given
        InsertCategoryDTO categoryDTO = InsertCategoryDTO.builder().name("BackEnd").build();
        InsertCategoryDTO categoryDTO2 = InsertCategoryDTO.builder().name("BackEnd").blog(blog).build();

        //when
        Long id = categoryService.saveCategory(categoryDTO2);
        Category category = categoryService.getCategory(id);

        //then
        assertThrows(IllegalArgumentException.class, () -> {
            categoryService.saveCategory(categoryDTO);
        });

        assertEquals(category.getId(), id);
        System.out.println(category);
    }

    @Test
    protected void 카테고리_변경() throws Exception {
        //given
        InsertCategoryDTO categoryDTO = InsertCategoryDTO.builder().name("BackEnd").blog(blog).build();
        Long id = categoryService.saveCategory(categoryDTO);

        //when
        String updatedName = "back-end";
        UpdateCategoryDTO updateCategoryDTO = UpdateCategoryDTO.builder().id(id).name(updatedName).build();
        categoryService.updateCategory(updateCategoryDTO);
        Category category = categoryService.getCategory(id);

        //then
        assertEquals(category.getName(), updatedName);
        System.out.println(category);
    }

    @Test
    protected void 특정_블로그의_카테고리_목록_가져오기() throws Exception {
        //given
        InsertCategoryDTO categoryDTO = InsertCategoryDTO.builder().name("BackEnd").blog(blog).build();
        InsertCategoryDTO categoryDTO2 = InsertCategoryDTO.builder().name("FrontEnd").blog(blog).build();

        //when
        categoryService.saveCategory(categoryDTO);
        categoryService.saveCategory(categoryDTO2);
        List<Category> categories = categoryService.getCategoriesByBlog(blog);

        //then
        assertEquals(categories.size(), 2);
        System.out.println(categories);

    }

    @Test
    public void 카테고리_삭제() throws Exception {
        //given
        InsertCategoryDTO categoryDTO = InsertCategoryDTO.builder().name("BackEnd").blog(blog).build();

        //when
        Long id = categoryService.saveCategory(categoryDTO);
        categoryService.deleteCategory(id);

        //then
        assertThrows(ResourceNotFoundException.class, () -> categoryService.getCategory(id));
    }
}