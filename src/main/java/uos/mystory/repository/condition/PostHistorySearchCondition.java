package uos.mystory.repository.condition;

import java.time.LocalDate;

public record PostHistorySearchCondition(Long postId, LocalDate from, LocalDate to){}
