package uos.mystory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uos.mystory.domain.Blog;
import uos.mystory.domain.User;
import uos.mystory.repository.querydsl.BlogQueryRepository;
import uos.mystory.repository.querydsl.regacy.BlogRepositoryCostom;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> , BlogQueryRepository {
    Long countByUrl(String url);

    Long countByUser(User user);
}
