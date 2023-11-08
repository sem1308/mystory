package uos.mystory.controller.api.v0;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uos.mystory.domain.Blog;
import uos.mystory.dto.mapping.insert.InsertCategoryDTO;
import uos.mystory.dto.mapping.select.SelectCategoryInfoDTO;
import uos.mystory.dto.mapping.update.UpdateCategoryDTO;
import uos.mystory.dto.request.create.CreateCategoryDTO;
import uos.mystory.dto.request.fix.FixCategoryDTO;
import uos.mystory.service.BlogService;
import uos.mystory.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/v0/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final BlogService blogService;

    @PostMapping()
    public ResponseEntity<Long> createCategory(@RequestBody CreateCategoryDTO categoryDTO){
        Blog blog = blogService.getBlog(categoryDTO.blogId());
        InsertCategoryDTO insertCategoryDTO = InsertCategoryDTO.builder()
                .name(categoryDTO.name())
                .blog(blog)
                .build();
        Long id = categoryService.saveCategory(insertCategoryDTO);
        return ResponseEntity.ok(id);
    }

    @GetMapping("/{blog_id}")
    public ResponseEntity<List<SelectCategoryInfoDTO>> getCategories(@PathVariable("blog_id") Long blogId){
        Blog blog = blogService.getBlog(blogId);
        List<SelectCategoryInfoDTO> categoryInfos = categoryService.getCategoryInfosByBlog(blog);
        return ResponseEntity.ok(categoryInfos);
    }

    @PutMapping()
    public ResponseEntity<String> updateCategory(@RequestBody FixCategoryDTO categoryDTO){
        UpdateCategoryDTO updateCategoryDTO = UpdateCategoryDTO.builder()
                .id(categoryDTO.id())
                .name(categoryDTO.name())
                .build();
        categoryService.updateCategory(updateCategoryDTO);
        return ResponseEntity.ok("success");
    }

    @DeleteMapping("/{category_id}")
    public ResponseEntity<String> deleteCategory(@PathVariable("category_id") Long categoryId){
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok("success");
    }

}
