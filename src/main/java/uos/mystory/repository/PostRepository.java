package uos.mystory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uos.mystory.domain.Post;
import uos.mystory.repository.querydsl.PostQueryRepository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>, PostQueryRepository {
    Long countByUrl(String url);
}
