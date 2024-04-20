package uos.mystory.repository.querydsl;

import uos.mystory.dto.mapping.select.SelectHistoryDTO;
import uos.mystory.dto.mapping.select.SelectHistoryVisitsDTO;
import uos.mystory.repository.condition.HistorySearchCondition;

import java.util.List;

public interface BlogHistoryQueryRepository {
    List<SelectHistoryDTO> findAllByBlogIdGroupByDateAndVisitedPath(Long blogId);

    List<SelectHistoryDTO> findAllByConditionGroupByDateAndVisitedPath(HistorySearchCondition condition);

    List<SelectHistoryVisitsDTO> getVisitsByBlogGroupByDate(Long blogId);
}
