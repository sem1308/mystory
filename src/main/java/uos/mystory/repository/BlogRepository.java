package uos.mystory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uos.mystory.domain.Blog;
import uos.mystory.domain.User;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {
    Long countByUrl(String url);
    Long countByUser(User user);
}
