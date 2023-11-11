package uos.mystory.dto.mapping.select;

import com.querydsl.core.annotations.QueryProjection;

import java.time.LocalDateTime;

public record SelectCommentInfoDTO (
    Long id,
    String content,
    LocalDateTime latestUpdatedDateTime,
    LocalDateTime createdDateTime
){
    @QueryProjection
    public SelectCommentInfoDTO {}
}
