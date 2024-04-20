package uos.mystory.repository.querydsl;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uos.mystory.dto.mapping.select.SelectCommentInfoDTO;
import uos.mystory.repository.condition.CommentSearchCondition;

import java.util.List;
import java.util.Optional;

public interface CommentQueryRepository {

    Page<SelectCommentInfoDTO> findAll(@NotNull CommentSearchCondition condition, @NotNull Pageable pageable);

    List<SelectCommentInfoDTO> findAll(@NotNull CommentSearchCondition condition);

    Optional<SelectCommentInfoDTO> findDtoById(Long commentId);
}
