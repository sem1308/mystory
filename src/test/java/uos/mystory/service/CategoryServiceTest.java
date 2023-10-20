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

        //when
        Long id = categoryService.saveCategory(categoryDTO);
        Category category = categoryService.getCategory(id);

        //then
        assertEquals(category.getId(), id);

    }
}