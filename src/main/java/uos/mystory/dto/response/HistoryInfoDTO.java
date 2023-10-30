package uos.mystory.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import uos.mystory.dto.mapping.select.SelectHistoryDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class HistoryInfoDTO {
    protected Long id;
    protected Map<LocalDate, VisitsWrapper> historyInfos;
    protected Long totalVisits;

    public static HistoryInfoDTO of(Long id, List<SelectHistoryDTO> selectHistoryDTOS){
        // 날짜에 따른 이력 정보 생성
        AtomicLong totalVisits = new AtomicLong(0L);
        Map<LocalDate, VisitsWrapper> historyInfoDTOSByDate = selectHistoryDTOS.stream()
                .collect(Collectors.groupingBy(
                        SelectHistoryDTO::date,
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
        return new HistoryInfoDTO(id, historyInfoDTOSByDate, totalVisits.get());
    }
}