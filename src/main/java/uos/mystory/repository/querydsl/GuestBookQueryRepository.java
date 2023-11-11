package uos.mystory.repository.querydsl;

import com.querydsl.core.types.dsl.BooleanExpression;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import uos.mystory.domain.GuestBook;
import uos.mystory.domain.QGuestBook;
import uos.mystory.dto.mapping.select.QSelectGuestBookInfoDTO;
import uos.mystory.dto.mapping.select.SelectGuestBookInfoDTO;
import uos.mystory.repository.condition.GuestBookSearchCondition;

import java.util.List;
import java.util.Optional;

import static uos.mystory.domain.QGuestBook.guestBook;

@Repository
public class GuestBookQueryRepository extends Querydsl4RepositorySupport<GuestBook, QGuestBook>{

    public GuestBookQueryRepository() {
        super(GuestBook.class, guestBook);
    }

    public Page<SelectGuestBookInfoDTO> findAll(@NotNull GuestBookSearchCondition condition, @NotNull Pageable pageable) {
        //== 자동 countQuery 생성 방법==//
        return applyPagination(pageable,
                select(new QSelectGuestBookInfoDTO(
                        guestBook.id,
                        guestBook.content,
                        guestBook.createdDateTime
                ))
                        .from(guestBook)
                        .where(
                                blogIdEq(condition.blogId())
                        ));
    }

    public List<SelectGuestBookInfoDTO> findAll(@NotNull GuestBookSearchCondition condition) {
        //== 자동 countQuery 생성 방법==//
        return select(new QSelectGuestBookInfoDTO(
                    guestBook.id,
                    guestBook.content,
                    guestBook.createdDateTime
                ))
                .from(guestBook)
                .where(
                        blogIdEq(condition.blogId())
                )
                .fetch();
    }

    public Optional<SelectGuestBookInfoDTO> findById(Long guestBookId) {
        //== 자동 countQuery 생성 방법==//
        return Optional.ofNullable(
                select(new QSelectGuestBookInfoDTO(
                        guestBook.id,
                        guestBook.content,
                        guestBook.createdDateTime
                ))
                .from(guestBook)
                .where(
                        guestBook.id.eq(guestBookId)
                )
                .fetchOne());
    }


    private BooleanExpression blogIdEq(Long blogId) {
        return blogId != null ?  guestBook.blog.id.eq(blogId) :null;
    }
    private BooleanExpression guestBookIdEq(Long guestBookId) {
        return guestBookId != null ?  guestBook.id.eq(guestBookId) :null;
    }
}
