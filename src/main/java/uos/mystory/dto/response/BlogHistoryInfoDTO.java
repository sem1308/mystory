package uos.mystory.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import uos.mystory.dto.mapping.select.SelectBlogHistoryDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BlogHistoryInfoDTO{
    private Long blogId;
    private Map<LocalDate, HistoryInfoDTO> historyInfos;
    private Long totalVisits;

    public static BlogHistoryInfoDTO of(Long blogId, List<SelectBlogHistoryDTO> selectBlogHistoryDTOS){
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
}
