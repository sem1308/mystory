package uos.mystory.repository.querydsl;

import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.stereotype.Repository;
import uos.mystory.domain.history.BlogHistory;
import uos.mystory.domain.history.QBlogHistory;
import uos.mystory.dto.mapping.select.*;
import uos.mystory.repository.condition.HistorySearchCondition;

import java.time.LocalDate;
import java.util.List;

import static uos.mystory.domain.history.QBlogHistory.blogHistory;

@Repository
public class BlogHistoryQueryRepository extends Querydsl4RepositorySupport<BlogHistory, QBlogHistory>{

    public BlogHistoryQueryRepository() {
        super(BlogHistory.class, blogHistory);
    }

    public List<SelectHistoryDTO> findAllByBlogIdGroupByDateAndVisitedPath(Long blogId) {
        return select(new QSelectHistoryDTO(
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

    public List<SelectHistoryDTO> findAllByConditionGroupByDateAndVisitedPath(HistorySearchCondition condition) {
        return select(new QSelectHistoryDTO(
                blogHistory.blog.id,
                blogHistory.createdDate,
                blogHistory.path,
                blogHistory.count()
        ))
                .from(blogHistory)
                .where(
                        blogHistory.blog.id.eq(condition.id()),
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
