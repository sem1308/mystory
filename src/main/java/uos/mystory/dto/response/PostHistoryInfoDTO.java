package uos.mystory.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import uos.mystory.dto.mapping.select.SelectPostHistoryDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PostHistoryInfoDTO {
    private Long postId;
    private Map<LocalDate, VisitsWrapper> historyInfos;
    private Long totalVisits;

    public static PostHistoryInfoDTO of(Long postId, List<SelectPostHistoryDTO> selectPostHistoryDTOS){
        // 날짜에 따른 이력 정보 생성
        AtomicLong totalVisits = new AtomicLong(0L);
        Map<LocalDate, VisitsWrapper> historyInfoDTOSByDate = selectPostHistoryDTOS.stream()
                .collect(Collectors.groupingBy(
                        SelectPostHistoryDTO::date,
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                list -> {
                                    AtomicLong visits = new AtomicLong(0L);
                                    List<VisitsInfoDTO> histories = list.stream()
                                            .peek(historyInfo -> visits.addAndGet(historyInfo.visits()))
                                            .map(historyInfo -> new VisitsInfoDTO(historyInfo.path(), historyInfo.visits()))
                                            .collect(Collectors.toList());
                                    totalVisits.addAndGet(visits.get());
                                    return new VisitsWrapper(histories, visits.get());
                                }
                        )
                ));
        return new PostHistoryInfoDTO(postId, historyInfoDTOSByDate, totalVisits.get());
    }
}
