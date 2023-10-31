package uos.mystory.service;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uos.mystory.domain.Comment;
import uos.mystory.dto.mapping.insert.InsertCommentDTO;
import uos.mystory.dto.mapping.update.UpdateCommentDTO;
import uos.mystory.exception.ResourceNotFoundException;
import uos.mystory.exception.massage.MessageManager;
import uos.mystory.repository.CommentRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
    private final CommentRepository commentRepository;

    /**
     * @Title 댓글 생성하기
     * @param insertCommentDTO
     * @return 댓글 번호
     */
    public Long saveComment(@NotNull InsertCommentDTO insertCommentDTO) {
        Comment comment = Comment.create(insertCommentDTO);
        return commentRepository.save(comment).getId();
    }

    /**
     * @Title 댓글 정보 수정하기
     * @param updateCommentDTO
     */
    public void updateComment(@NotNull UpdateCommentDTO updateCommentDTO) {
        Comment comment = getComment(updateCommentDTO.getId());
        comment.update(updateCommentDTO);
    }

    /**
     * @Title 댓글 번호로 댓글 가져오기
     * @param id
     * @return 댓글 엔티티
     */
    @Transactional(readOnly = true)
    public Comment getComment(Long id) {
        return commentRepository.findById(id).orElseThrow(()->new ResourceNotFoundException(MessageManager.getMessage("error.notfound.comment")));
    }

    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
}
