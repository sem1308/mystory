package uos.mystory.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoryInfoDTO {
    private List<VisitsInfoDTO> histories;
    private Long totalVisits;
}
