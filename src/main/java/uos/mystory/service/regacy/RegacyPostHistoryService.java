package uos.mystory.service.regacy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uos.mystory.dto.mapping.select.SelectPostHistoryDTO;
import uos.mystory.dto.response.PostHistoryInfoDTO;
import uos.mystory.repository.PostHistoryRepository;
import uos.mystory.repository.condition.PostHistorySearchCondition;
import uos.mystory.repository.querydsl.regacy.RegacyPostHistoryQueryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RegacyPostHistoryService {
    private final PostHistoryRepository postHistoryRepository;
    private final RegacyPostHistoryQueryRepository postHistoryQueryRepository;

    /**
     * @Title 게시글 번호로 게시글 이력 얻기
     * @param postId
     * @return 게시글 이력 DTO
     */
    public List<SelectPostHistoryDTO> getPostHistoryInfoDTOs(Long postId) {
        return postHistoryQueryRepository.findAllByPostIdGroupByDateAndVisitedPath(postId);
    }

    /**
     * @Title 게시글 번호로 정리된 게시글 이력 얻기
     * @param postId
     * @return 정리된 게시글 이력
     */
    public PostHistoryInfoDTO getPostHistories(Long postId) {
        List<SelectPostHistoryDTO> selectBlogHistoryDTOS = postHistoryQueryRepository.findAllByPostIdGroupByDateAndVisitedPath(postId);
        return PostHistoryInfoDTO.of(postId, selectBlogHistoryDTOS);
    }

    /**
     * @Title 특정 조건으로(postId, from, to) 정리된 게시글 이력 얻기 ex) 특정 날짜의 게시글 이력 얻기
     * @param condition
     * @return 정리된 게시글 이력
     */
    public PostHistoryInfoDTO getPostHistories(PostHistorySearchCondition condition) {
        List<SelectPostHistoryDTO> selectPostHistoryDTOS = postHistoryQueryRepository.findAllByConditionGroupByDateAndVisitedPath(condition);
        return PostHistoryInfoDTO.of(condition.postId(), selectPostHistoryDTOS);
    }
}
