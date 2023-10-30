package uos.mystory.repository.querydsl.regacy;

import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.stereotype.Repository;
import uos.mystory.domain.history.PostHistory;
import uos.mystory.domain.history.QPostHistory;
import uos.mystory.dto.mapping.select.QSelectPostHistoryDTO;
import uos.mystory.dto.mapping.select.QSelectHistoryVisitsDTO;
import uos.mystory.dto.mapping.select.SelectPostHistoryDTO;
import uos.mystory.dto.mapping.select.SelectHistoryVisitsDTO;
import uos.mystory.repository.condition.PostHistorySearchCondition;
import uos.mystory.repository.querydsl.Querydsl4RepositorySupport;

import java.time.LocalDate;
import java.util.List;

import static uos.mystory.domain.history.QPostHistory.postHistory;

@Repository
public class RegacyPostHistoryQueryRepository extends Querydsl4RepositorySupport<PostHistory, QPostHistory> {

    public RegacyPostHistoryQueryRepository() {
        super(PostHistory.class, postHistory);
    }

    public List<SelectPostHistoryDTO> findAllByPostIdGroupByDateAndVisitedPath(Long blogId) {
        return select(new QSelectPostHistoryDTO(
                        postHistory.post.id,
                        postHistory.createdDate,
                        postHistory.path,
                        postHistory.count()
                ))
                .from(postHistory)
                .where(postHistory.post.id.eq(blogId))
                .groupBy(postHistory.createdDate, postHistory.path)
                .fetch();
    }

    public List<SelectPostHistoryDTO> findAllByConditionGroupByDateAndVisitedPath(PostHistorySearchCondition condition) {
        return select(new QSelectPostHistoryDTO(
                postHistory.post.id,
                postHistory.createdDate,
                postHistory.path,
                postHistory.count()
        ))
                .from(postHistory)
                .where(
                        postIdEq(condition.postId()),
                        dateBetween(condition.from(), condition.to())
                        )
                .groupBy(postHistory.createdDate, postHistory.path)
                .fetch();
    }

    public List<SelectHistoryVisitsDTO> getVisitsByBlogGroupByDate(Long blogId) {
        return select(new QSelectHistoryVisitsDTO(postHistory.createdDate, postHistory.count()))
                .from(postHistory)
                .where(postHistory.post.id.eq(blogId))
                .groupBy(postHistory.createdDate)
                .fetch();
    }

    private BooleanExpression postIdEq(Long postId) {
        return postId != null ? postHistory.post.id.eq(postId) :null;
    }

    private BooleanExpression dateBetween(LocalDate from, LocalDate to) {
        return (from != null && to != null) ? postHistory.createdDate.between(from,to) :null;
    }

}
