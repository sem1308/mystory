package uos.mystory.dto.mapping.select;

import com.querydsl.core.annotations.QueryProjection;

public record SelectBlogInfoDTO(Long id, String name, String url, String description, Integer visits) {
    @QueryProjection
    public SelectBlogInfoDTO {}
}
