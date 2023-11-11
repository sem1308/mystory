package uos.mystory.repository.querydsl;

import com.querydsl.core.types.dsl.BooleanExpression;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import uos.mystory.domain.Comment;
import uos.mystory.domain.QComment;
import uos.mystory.dto.mapping.select.QSelectCategoryInfoDTO;
import uos.mystory.dto.mapping.select.QSelectCommentInfoDTO;
import uos.mystory.dto.mapping.select.SelectCommentInfoDTO;
import uos.mystory.repository.condition.CommentSearchCondition;

import java.util.List;
import java.util.Optional;

import static uos.mystory.domain.QComment.comment;

@Repository
public class CommentQueryRepository extends Querydsl4RepositorySupport<Comment, QComment>{

    public CommentQueryRepository() {
        super(Comment.class, comment);
    }

    public Page<SelectCommentInfoDTO> findAll(@NotNull CommentSearchCondition condition, @NotNull Pageable pageable) {
        //== 자동 countQuery 생성 방법==//
        return applyPagination(pageable,
                select(new QSelectCommentInfoDTO(
                        comment.id,
                        comment.content,
                        comment.latestUpdatedDateTime,
                        comment.createdDateTime
                ))
                        .from(comment)
                        .where(
                                postIdEq(condition.postId())
                        ));
    }

    public List<SelectCommentInfoDTO> findAll(@NotNull CommentSearchCondition condition) {
        //== 자동 countQuery 생성 방법==//
        return select(new QSelectCommentInfoDTO(
                    comment.id,
                    comment.content,
                    comment.latestUpdatedDateTime,
                    comment.createdDateTime
                ))
                .from(comment)
                .where(
                        postIdEq(condition.postId())
                )
                .fetch();
    }

    public Optional<SelectCommentInfoDTO> findById(Long commentId) {
        //== 자동 countQuery 생성 방법==//
        return Optional.ofNullable(
                select(new QSelectCommentInfoDTO(
                        comment.id,
                        comment.content,
                        comment.latestUpdatedDateTime,
                        comment.createdDateTime
                ))
                .from(comment)
                .where(
                        comment.id.eq(commentId)
                )
                .fetchOne());
    }


    private BooleanExpression postIdEq(Long postId) {
        return postId != null ?  comment.post.id.eq(postId) :null;
    }
    private BooleanExpression commentIdEq(Long commentId) {
        return commentId != null ?  comment.id.eq(commentId) :null;
    }
}
