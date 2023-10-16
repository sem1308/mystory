package uos.mystory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uos.mystory.domain.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
