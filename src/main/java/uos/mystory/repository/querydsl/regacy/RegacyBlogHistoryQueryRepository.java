package uos.mystory.repository.querydsl.regacy;

import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.stereotype.Repository;
import uos.mystory.domain.history.BlogHistory;
import uos.mystory.domain.history.QBlogHistory;
import uos.mystory.dto.mapping.select.QSelectBlogHistoryDTO;
import uos.mystory.dto.mapping.select.QSelectHistoryVisitsDTO;
import uos.mystory.dto.mapping.select.SelectBlogHistoryDTO;
import uos.mystory.dto.mapping.select.SelectHistoryVisitsDTO;
import uos.mystory.repository.condition.BlogHistorySearchCondition;
import uos.mystory.repository.querydsl.Querydsl4RepositorySupport;

import java.time.LocalDate;
import java.util.List;

import static uos.mystory.domain.history.QBlogHistory.blogHistory;

@Repository
public class RegacyBlogHistoryQueryRepository extends Querydsl4RepositorySupport<BlogHistory, QBlogHistory> {

    public RegacyBlogHistoryQueryRepository() {
        super(BlogHistory.class, blogHistory);
    }

    public List<SelectBlogHistoryDTO> findAllByBlogIdGroupByDateAndVisitedPath(Long blogId) {
        return select(new QSelectBlogHistoryDTO(
                        blogHistory.blog.id,
                        blogHistory.createdDate,
                        blogHistory.path,
                        blogHistory.count()
                ))
                .from(blogHistory)
                .where(blogHistory.blog.id.eq(blogId))
                .groupBy(blogHistory.createdDate, blogHistory.path)
                .fetch();
    }

    public List<SelectBlogHistoryDTO> findAllByConditionGroupByDateAndVisitedPath(BlogHistorySearchCondition condition) {
        return select(new QSelectBlogHistoryDTO(
                blogHistory.blog.id,
                blogHistory.createdDate,
                blogHistory.path,
                blogHistory.count()
        ))
                .from(blogHistory)
                .where(
                        blogIdEq(condition.blogId()),
                        dateBetween(condition.from(), condition.to())
                        )
                .groupBy(blogHistory.createdDate, blogHistory.path)
                .fetch();
    }


    public List<SelectHistoryVisitsDTO> getVisitsByBlogGroupByDate(Long blogId) {
        return select(new QSelectHistoryVisitsDTO(blogHistory.createdDate, blogHistory.count()))
                .from(blogHistory)
                .where(blogHistory.blog.id.eq(blogId))
                .groupBy(blogHistory.createdDate)
                .fetch();
    }


    private BooleanExpression blogIdEq(Long blogId) {
        return blogId != null ? blogHistory.blog.id.eq(blogId) :null;
    }

    private BooleanExpression dateBetween(LocalDate from, LocalDate to) {
        return (from != null && to != null) ? blogHistory.createdDate.between(from,to) :null;
    }

}
