package uos.mystory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uos.mystory.domain.Blog;
import uos.mystory.domain.Category;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAllByBlog(Blog blog);
}
