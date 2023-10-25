package uos.mystory.repository.querydsl;

import org.springframework.stereotype.Repository;
import uos.mystory.domain.history.BlogHistory;
import uos.mystory.dto.mapping.select.QSelectBlogHistoryDTO;
import uos.mystory.dto.mapping.select.QSelectHistoryVisitsDTO;
import uos.mystory.dto.mapping.select.SelectBlogHistoryDTO;
import uos.mystory.dto.mapping.select.SelectHistoryVisitsDTO;

import java.util.List;

import static uos.mystory.domain.history.QBlogHistory.blogHistory;

@Repository
public class BlogHistoryQueryRepository extends Querydsl4RepositorySupport{

    public BlogHistoryQueryRepository() {
        super(BlogHistory.class);
    }

    public List<SelectBlogHistoryDTO> findAllByBlogGroupByDateAndVisitedPath(Long blogId) {
        return select(new QSelectBlogHistoryDTO(
                        blogHistory.blog.id,
                        blogHistory.createdDate,
                        blogHistory.path,
                        blogHistory.count()
                ))
                .from(blogHistory)
                .where(blogHistory.blog.id.eq(blogId))
                .groupBy(blogHistory.createdDate, blogHistory.path).fetch();
    }

    public List<SelectHistoryVisitsDTO> getVisitsByBlogGroupByDate(Long blogId) {
        return select(new QSelectHistoryVisitsDTO(blogHistory.createdDate, blogHistory.count()))
                .from(blogHistory)
                .where(blogHistory.blog.id.eq(blogId))
                .groupBy(blogHistory.createdDate).fetch();
    }
}
