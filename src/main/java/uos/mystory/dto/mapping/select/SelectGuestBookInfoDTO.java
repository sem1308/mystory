package uos.mystory.dto.mapping.select;

import com.querydsl.core.annotations.QueryProjection;

import java.time.LocalDateTime;

public record SelectGuestBookInfoDTO (
        Long id,
        String content,
        LocalDateTime createdDateTime
){
    @QueryProjection
    public SelectGuestBookInfoDTO {}
}
