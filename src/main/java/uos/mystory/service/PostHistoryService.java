package uos.mystory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uos.mystory.dto.mapping.select.SelectHistoryDTO;
import uos.mystory.dto.response.HistoryInfoDTO;
import uos.mystory.repository.PostHistoryRepository;
import uos.mystory.repository.condition.HistorySearchCondition;
import uos.mystory.repository.querydsl.PostHistoryQueryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostHistoryService implements HistoryService{
    private final PostHistoryRepository postHistoryRepository;
    private final PostHistoryQueryRepository postHistoryQueryRepository;

    /**
     * @Title 게시글 번호로 게시글 이력 얻기
     * @param postId
     * @return 게시글 이력 DTO
     */
    @Override
    public List<SelectHistoryDTO> getHistoryInfoDTOs(Long postId) {
        return postHistoryQueryRepository.findAllByPostIdGroupByDateAndVisitedPath(postId);
    }

    /**
     * @Title 게시글 번호로 정리된 게시글 이력 얻기
     * @param postId
     * @return 정리된 게시글 이력
     */
    @Override
    public HistoryInfoDTO getHistories(Long postId) {
        List<SelectHistoryDTO> selectBlogHistoryDTOS = postHistoryQueryRepository.findAllByPostIdGroupByDateAndVisitedPath(postId);
        return HistoryInfoDTO.of(postId, selectBlogHistoryDTOS);
    }

    /**
     * @Title 특정 조건으로(postId, from, to) 정리된 게시글 이력 얻기 ex) 특정 날짜의 게시글 이력 얻기
     * @param condition
     * @return 정리된 게시글 이력
     */
    @Override
    public HistoryInfoDTO getHistories(HistorySearchCondition condition) {
        List<SelectHistoryDTO> selectHistoryDTOS = postHistoryQueryRepository.findAllByConditionGroupByDateAndVisitedPath(condition);
        return HistoryInfoDTO.of(condition.id(), selectHistoryDTOS);
    }
}
