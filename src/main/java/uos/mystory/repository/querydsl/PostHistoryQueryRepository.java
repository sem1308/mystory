package uos.mystory.repository.querydsl;

import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.stereotype.Repository;
import uos.mystory.domain.history.PostHistory;
import uos.mystory.domain.history.QPostHistory;
import uos.mystory.dto.mapping.select.QSelectHistoryDTO;
import uos.mystory.dto.mapping.select.QSelectHistoryVisitsDTO;
import uos.mystory.dto.mapping.select.SelectHistoryDTO;
import uos.mystory.dto.mapping.select.SelectHistoryVisitsDTO;
import uos.mystory.repository.condition.HistorySearchCondition;

import java.time.LocalDate;
import java.util.List;

import static uos.mystory.domain.history.QPostHistory.postHistory;

public interface PostHistoryQueryRepository {

    List<SelectHistoryDTO> findAllByPostIdGroupByDateAndVisitedPath(Long postId);

    List<SelectHistoryDTO> findAllByConditionGroupByDateAndVisitedPath(HistorySearchCondition condition);

    List<SelectHistoryVisitsDTO> getVisitsByBlogGroupByDate(Long postId);
}
