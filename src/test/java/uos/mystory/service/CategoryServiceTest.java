package uos.mystory.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import uos.mystory.domain.Blog;
import uos.mystory.domain.Category;
import uos.mystory.dto.mapping.insert.InsertBlogDTO;
import uos.mystory.dto.mapping.insert.InsertCategoryDTO;
import uos.mystory.dto.mapping.update.UpdateCategoryDTO;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CategoryServiceTest extends BlogServiceTest{
    @Autowired
    CategoryService categoryService;

    Blog blog;

    @BeforeEach
    public void setup() {
        super.setup();
        Long blogId = blogService.saveBlog(InsertBlogDTO.builder().name("Dev").url("https://han-dev.mystory.com").description("기본 블로그").user(user).build());
        this.blog = blogService.getBlog(blogId);
    }

    @Disabled("상속된 메서드는 실행하지 않습니다.")
    public void 블로그_생성() {}
    @Disabled("상속된 메서드는 실행하지 않습니다.")
    public void 블로그_변경() {}

    @Test
    protected void 카테고리_생성() throws Exception {
        //given
        InsertCategoryDTO categoryDTO = InsertCategoryDTO.builder().name("BackEnd").build();
        InsertCategoryDTO categoryDTO2 = InsertCategoryDTO.builder().name("BackEnd").blog(blog).build();

        //when
        Long id = categoryService.saveCategory(categoryDTO2);
        Category category = categoryService.getCategory(id);

        //then
        assertThrows(NullPointerException.class, () -> {
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
}