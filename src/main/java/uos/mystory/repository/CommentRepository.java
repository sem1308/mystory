package uos.mystory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uos.mystory.domain.Blog;
import uos.mystory.domain.Category;
import uos.mystory.domain.Comment;
import uos.mystory.domain.Post;
import uos.mystory.repository.querydsl.CommentQueryRepository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long>, CommentQueryRepository {
    List<Comment> findAllByPost(Post post);
}
