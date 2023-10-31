package uos.mystory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uos.mystory.dto.mapping.select.SelectHistoryDTO;
import uos.mystory.dto.response.HistoryInfoDTO;
import uos.mystory.repository.BlogHistoryRepository;
import uos.mystory.repository.condition.HistorySearchCondition;
import uos.mystory.repository.querydsl.BlogHistoryQueryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BlogHistoryService implements HistoryService{
    private final BlogHistoryRepository blogHistoryRepository;
    private final BlogHistoryQueryRepository blogHistoryQueryRepository;

    /**
     * @title 블로그 번호로 블로그 이력 얻기
     * @param blogId
     * @return 블로그 이력 DTO
     */
    public List<SelectHistoryDTO> getHistoryInfoDTOs(Long blogId) {
        return blogHistoryQueryRepository.findAllByBlogIdGroupByDateAndVisitedPath(blogId);
    }

    /**
     * @title 블로그 번호로 정리된 블로그 이력 얻기
     * @param blogId
     * @return 정리된 블로그 이력
     */
    public HistoryInfoDTO getHistories(Long blogId) {
        List<SelectHistoryDTO> selectBlogHistoryDTOS = blogHistoryQueryRepository.findAllByBlogIdGroupByDateAndVisitedPath(blogId);
        return HistoryInfoDTO.of(blogId, selectBlogHistoryDTOS);
    }

    /**
     * @title 특정 조건으로(blogId, from, to) 정리된 블로그 이력 얻기 ex) 특정 날짜의 블로그 이력 얻기
     * @param condition
     * @return 정리된 블로그 이력
     */
    public HistoryInfoDTO getHistories(HistorySearchCondition condition) {
        List<SelectHistoryDTO> selectBlogHistoryDTOS = blogHistoryQueryRepository.findAllByConditionGroupByDateAndVisitedPath(condition);
        return HistoryInfoDTO.of(condition.id(), selectBlogHistoryDTOS);
    }
}
