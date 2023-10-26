package uos.mystory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uos.mystory.domain.enums.VisitedPath;
import uos.mystory.dto.mapping.select.SelectBlogHistoryDTO;
import uos.mystory.dto.response.BlogHistoryInfoDTO;
import uos.mystory.dto.response.HistoryInfoDTO;
import uos.mystory.dto.response.VisitsInfoDTO;
import uos.mystory.repository.BlogHistoryRepository;
import uos.mystory.repository.querydsl.BlogHistoryQueryRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BlogHistoryService {
    private final BlogHistoryRepository blogHistoryRepository;
    private final BlogHistoryQueryRepository blogHistoryQueryRepository;

    public List<SelectBlogHistoryDTO> getBlogHistoryInfoDTOsByBlogId(Long blogId) {
        return blogHistoryQueryRepository.findAllByBlogGroupByDateAndVisitedPath(blogId);
    }

    public BlogHistoryInfoDTO getBlogHistoriesByBlogId(Long blogId) {
        List<SelectBlogHistoryDTO> selectBlogHistoryDTOS = blogHistoryQueryRepository.findAllByBlogGroupByDateAndVisitedPath(blogId);

        // 날짜에 따른 이력 정보 생성
        AtomicLong totalVisits = new AtomicLong(0L);
        Map<LocalDate, HistoryInfoDTO> blogHistoryInfoDTOSByDate = selectBlogHistoryDTOS.stream()
                .collect(Collectors.groupingBy(
                        SelectBlogHistoryDTO::date,
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                list -> {
                                    AtomicLong visits = new AtomicLong(0L);
                                    List<VisitsInfoDTO> histories = list.stream()
                                            .peek(blogHistoryInfoDTO -> visits.addAndGet(blogHistoryInfoDTO.visits()))
                                            .map(blogHistoryInfoDTO -> new VisitsInfoDTO(blogHistoryInfoDTO.path(), blogHistoryInfoDTO.visits()))
                                            .collect(Collectors.toList());
                                    totalVisits.addAndGet(visits.get());
                                    return new HistoryInfoDTO(histories, visits.get());
                                }
                        )
                ));

        return new BlogHistoryInfoDTO(blogId, blogHistoryInfoDTOSByDate, totalVisits.get());
    }

    @Deprecated(since = "it's regacy code. use getHistoriesByBlogId")
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
