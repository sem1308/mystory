package uos.mystory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uos.mystory.domain.Blog;
import uos.mystory.domain.User;
import uos.mystory.repository.querydsl.BlogRepositoryCostom;

public interface BlogRepository extends JpaRepository<Blog, Long> , BlogRepositoryCostom {
    Long countByUrl(String url);

    Long countByUser(User user);
}
