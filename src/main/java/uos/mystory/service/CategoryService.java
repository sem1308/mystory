package uos.mystory.service;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uos.mystory.domain.Blog;
import uos.mystory.domain.Category;
import uos.mystory.dto.mapping.insert.InsertCategoryDTO;
import uos.mystory.dto.mapping.update.UpdateCategoryDTO;
import uos.mystory.exception.ResourceNotFoundException;
import uos.mystory.exception.massage.MessageManager;
import uos.mystory.repository.CategoryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {
    private final CategoryRepository categoryRepository;

    /**
     * @title 특정 블로그의 카테고리 생성
     * @param insertCategoryDTO
     * @return 카테고리 번호
     */
    public Long saveCategory(@NotNull InsertCategoryDTO insertCategoryDTO) {
        Category category = Category.create(insertCategoryDTO);
        return categoryRepository.save(category).getId();
    }

    /**
     * @title 카테고리 정보 변경
     * @param updateCategoryDTO
     */
    public void updateCategory(@NotNull UpdateCategoryDTO updateCategoryDTO) {
        Category category = getCategory(updateCategoryDTO.getId());
        category.update(updateCategoryDTO);
    }

    /**
     * @title 카테고리 번호로 카테고리 엔티티 가져오기
     * @param id
     * @return 카테고리 엔티티
     */
    @Transactional(readOnly = true)
    public Category getCategory(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(MessageManager.getMessage("error.notfound.category")));
    }

    /**
     * @title 특정 블로그의 카테고리 목록 가져오기
     * @param blog
     * @return
     */
    @Transactional(readOnly = true)
    public List<Category> getCategoriesByBlog(Blog blog) {
        return categoryRepository.findAllByBlog(blog);
    }

    /**
     * @title 카테고리 번호로 카테고리 삭제
     * @param id
     */
    public void deleteCategory(Long id){
        categoryRepository.deleteById(id);
    }
}
