package uos.mystory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uos.mystory.domain.history.BlogHistory;
import uos.mystory.repository.querydsl.BlogHistoryQueryRepository;
import uos.mystory.repository.querydsl.BlogQueryRepository;

import java.util.List;

@Repository
public interface BlogHistoryRepository extends JpaRepository<BlogHistory, Long>, BlogHistoryQueryRepository {
    @Deprecated
    @Query("SELECT BH.blog.id, BH.createdDate, BH.path, count(*) FROM BlogHistory BH WHERE BH.blog.id=:blogId GROUP BY BH.createdDate,BH.path")
    public List<Object[]> findAllByBlogGroupByDateAndVisitedPath(@Param("blogId") Long blogId);

    public List<BlogHistory> findAllByBlogId(Long blogId);
}
