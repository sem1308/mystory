package uos.mystory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uos.mystory.domain.Blog;
import uos.mystory.domain.Category;
import uos.mystory.repository.querydsl.CategoryQueryRepository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>, CategoryQueryRepository {
    List<Category> findAllByBlog(Blog blog);
}
