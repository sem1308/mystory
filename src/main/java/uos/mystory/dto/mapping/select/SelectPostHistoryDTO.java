package uos.mystory.dto.mapping.select;

import com.querydsl.core.annotations.QueryProjection;
import uos.mystory.domain.enums.VisitedPath;

import java.time.LocalDate;

public record SelectPostHistoryDTO (Long postId, LocalDate date, VisitedPath path, Long visits){
    @QueryProjection
    public SelectPostHistoryDTO {}
}
