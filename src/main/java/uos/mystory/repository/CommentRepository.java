package uos.mystory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uos.mystory.domain.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
}
