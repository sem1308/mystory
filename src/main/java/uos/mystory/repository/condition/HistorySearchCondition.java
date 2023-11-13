package uos.mystory.repository.condition;

import java.time.LocalDate;

public record HistorySearchCondition(
        Long id,
        LocalDate from,
        LocalDate to
) { }
