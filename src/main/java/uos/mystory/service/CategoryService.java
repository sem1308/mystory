package uos.mystory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uos.mystory.domain.Category;
import uos.mystory.dto.mapping.insert.InsertCategoryDTO;
import uos.mystory.exception.ResourceNotFoundException;
import uos.mystory.exception.massage.MessageManager;
import uos.mystory.repository.CategoryRepository;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    /**
     * @param insertCategoryDTO
     * @return 카테고리 번호
     * @title 특정 블로그의 카테고리 생성
     */
    public Long saveCategory(InsertCategoryDTO insertCategoryDTO) {
        Category category = Category.create(insertCategoryDTO.getName(), insertCategoryDTO.getBlog());
        return categoryRepository.save(category).getId();
    }

    public Category getCategory(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(MessageManager.getMessage("error.notfound.category")));
    }


}
