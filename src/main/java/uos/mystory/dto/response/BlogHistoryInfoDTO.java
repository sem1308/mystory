package uos.mystory.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogHistoryInfoDTO{
    private Long blogId;
    private Map<LocalDate, HistoryInfoDTO> historyInfos;
    private Long totalVisits;
}
