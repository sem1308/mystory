package uos.mystory.dto.mapping.select;

import com.querydsl.core.annotations.QueryProjection;

public record SelectCategoryInfoDTO (
        Long id,
        String name
){
    @QueryProjection
    public SelectCategoryInfoDTO{};
}
