package uos.mystory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uos.mystory.domain.Blog;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {
}
