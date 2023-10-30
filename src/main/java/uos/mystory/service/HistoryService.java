package uos.mystory.service;

import uos.mystory.dto.mapping.select.SelectHistoryDTO;
import uos.mystory.dto.response.HistoryInfoDTO;
import uos.mystory.dto.response.VisitsWrapper;
import uos.mystory.repository.condition.HistorySearchCondition;

import java.time.LocalDate;
import java.util.List;

public interface HistoryService {
    public List<SelectHistoryDTO> getHistoryInfoDTOs(Long id);

    public HistoryInfoDTO getHistories(Long id);

    public HistoryInfoDTO getHistories(HistorySearchCondition condition);
}
