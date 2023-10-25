package uos.mystory.dto.mapping.select;

import com.querydsl.core.annotations.QueryProjection;

import java.time.LocalDate;

public record SelectHistoryVisitsDTO(LocalDate date, Long visits) {
    @QueryProjection
    public SelectHistoryVisitsDTO {}
}
