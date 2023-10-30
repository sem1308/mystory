package uos.mystory.service.regacy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uos.mystory.domain.enums.VisitedPath;
import uos.mystory.dto.mapping.select.SelectBlogHistoryDTO;
import uos.mystory.dto.response.BlogHistoryInfoDTO;
import uos.mystory.repository.BlogHistoryRepository;
import uos.mystory.repository.condition.BlogHistorySearchCondition;
import uos.mystory.repository.querydsl.regacy.RegacyBlogHistoryQueryRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RegacyBlogHistoryService {
    private final BlogHistoryRepository blogHistoryRepository;
    private final RegacyBlogHistoryQueryRepository blogHistoryQueryRepository;

    /**
     * @Title 블로그 번호로 블로그 이력 얻기
     * @param blogId
     * @return 블로그 이력 DTO
     */
    public List<SelectBlogHistoryDTO> getBlogHistoryInfoDTOs(Long blogId) {
        return blogHistoryQueryRepository.findAllByBlogIdGroupByDateAndVisitedPath(blogId);
    }

    /**
     * @Title 블로그 번호로 정리된 블로그 이력 얻기
     * @param blogId
     * @return 정리된 블로그 이력
     */
    public BlogHistoryInfoDTO getBlogHistories(Long blogId) {
        List<SelectBlogHistoryDTO> selectBlogHistoryDTOS = blogHistoryQueryRepository.findAllByBlogIdGroupByDateAndVisitedPath(blogId);
        return BlogHistoryInfoDTO.of(blogId, selectBlogHistoryDTOS);
    }

    /**
     * @Title 특정 조건으로(blogId, from, to) 정리된 블로그 이력 얻기 ex) 특정 날짜의 블로그 이력 얻기
     * @param condition
     * @return 정리된 블로그 이력
     */
    public BlogHistoryInfoDTO getBlogHistories(BlogHistorySearchCondition condition) {
        List<SelectBlogHistoryDTO> selectBlogHistoryDTOS = blogHistoryQueryRepository.findAllByConditionGroupByDateAndVisitedPath(condition);
        return BlogHistoryInfoDTO.of(condition.blogId(), selectBlogHistoryDTOS);
    }

    @Deprecated(since = "it's regacy code. Use getHistoriesByBlogId")
    public List<SelectBlogHistoryDTO> getHistories(Long blogId) {
        List<Object[]> histories = blogHistoryRepository.findAllByBlogGroupByDateAndVisitedPath(blogId);

        List<SelectBlogHistoryDTO> historyInfoDTOS = new ArrayList<>();

        histories.forEach(history->{
            Long blogID = (Long) history[0];
            LocalDate date = (LocalDate) history[1];
            VisitedPath path = (VisitedPath) history[2];
            Long visits = (Long) history[3];

            // do something with entities
            historyInfoDTOS.add(new SelectBlogHistoryDTO(blogID, date, path, visits));
        });

        return historyInfoDTOS;
    }
}
