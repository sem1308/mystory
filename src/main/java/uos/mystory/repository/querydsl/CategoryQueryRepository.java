package uos.mystory.repository.querydsl;

import uos.mystory.dto.mapping.select.SelectCategoryInfoDTO;

import java.util.List;

public interface CategoryQueryRepository {
    List<SelectCategoryInfoDTO> findAll(Long blogId);
}
