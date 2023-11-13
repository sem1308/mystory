package uos.mystory.controller.api.v0;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uos.mystory.dto.response.HistoryInfoDTO;
import uos.mystory.repository.condition.HistorySearchCondition;
import uos.mystory.service.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v0/statistics")
public class StatisticsController {
    private final PostHistoryService postHistoryService;
    private final BlogHistoryService blogHistoryService;

    @PostMapping("/post")
    public ResponseEntity<HistoryInfoDTO> getPostStatistics(@RequestBody HistorySearchCondition condition){
        HistoryInfoDTO history = postHistoryService.getHistories(condition);
        return ResponseEntity.ok(history);
    }

    @PostMapping("/blog")
    public ResponseEntity<HistoryInfoDTO> getBlogStatistics(@RequestBody HistorySearchCondition condition){
        HistoryInfoDTO history = blogHistoryService.getHistories(condition);
        return ResponseEntity.ok(history);
    }
}
