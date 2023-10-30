package uos.mystory.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VisitsWrapper {
    private List<VisitsInfoDTO> visitsInfos;
    private Long totalVisits;
}
