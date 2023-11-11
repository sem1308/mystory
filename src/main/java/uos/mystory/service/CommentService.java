package uos.mystory.service;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uos.mystory.domain.Comment;
import uos.mystory.dto.mapping.insert.InsertCommentDTO;
import uos.mystory.dto.mapping.select.SelectCommentInfoDTO;
import uos.mystory.dto.mapping.update.UpdateCommentDTO;
import uos.mystory.dto.request.PageDTO;
import uos.mystory.dto.response.ResponseCommentDTO;
import uos.mystory.exception.ResourceNotFoundException;
import uos.mystory.exception.massage.MessageManager;
import uos.mystory.repository.CommentRepository;
import uos.mystory.repository.condition.CommentSearchCondition;
import uos.mystory.repository.querydsl.CommentQueryRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
    private final CommentRepository commentRepository;
    private final CommentQueryRepository commentQueryRepository;

    /**
     * @title 댓글 생성하기
     * @param insertCommentDTO
     * @return 댓글 번호
     */
    public Long saveComment(@NotNull InsertCommentDTO insertCommentDTO) {
        Comment comment = Comment.create(insertCommentDTO);
        return commentRepository.save(comment).getId();
    }

    /**
     * @title 댓글 정보 수정하기
     * @param updateCommentDTO
     */
    public void updateComment(@NotNull UpdateCommentDTO updateCommentDTO) {
        Comment comment = getComment(updateCommentDTO.getId());
        comment.update(updateCommentDTO);
    }

    /**
     * @title 댓글 번호로 댓글 가져오기
     * @param id
     * @return 댓글 엔티티
     */
    @Transactional(readOnly = true)
    public Comment getComment(Long id) {
        return commentRepository.findById(id).orElseThrow(()->new ResourceNotFoundException(MessageManager.getMessage("error.notfound.comment")));
    }

    /**
     * 댓글 번호로 필요한 댓글 정보 가져오기
     * @param id
     * @return 댓글 정보 DTO
     */
    @Transactional(readOnly = true)
    public SelectCommentInfoDTO getCommentInfo(Long id) {
        return commentQueryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(MessageManager.getMessage("error.notfound.comment")));
    }

    /**
     * 특정 조건에 따른 댓글 목록 가져오기
     * @param condition
     * @param pageable
     * @return
     */
    @Transactional(readOnly = true)
    public Page<SelectCommentInfoDTO> getCommentsByContidion(CommentSearchCondition condition, Pageable pageable){
        return commentQueryRepository.findAll(condition, pageable);
    }

    /**
     * @title 댓글 번호로 댓글 삭제
     * @param id
     */
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
}
