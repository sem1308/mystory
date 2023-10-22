package uos.mystory.dto.response;

import com.querydsl.core.annotations.QueryProjection;

public record BlogInfoDTO(Long id, String name, String url, String description, Integer visits) {
    @QueryProjection
    public BlogInfoDTO {}
}
