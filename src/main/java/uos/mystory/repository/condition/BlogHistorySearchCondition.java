package uos.mystory.repository.condition;

import java.time.LocalDate;

public record BlogHistorySearchCondition(
        Long blogId,
        LocalDate from,
        LocalDate to
){ }
