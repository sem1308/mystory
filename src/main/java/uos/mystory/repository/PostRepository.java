package uos.mystory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uos.mystory.domain.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Long countByUrl(String url);
}
