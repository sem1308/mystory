package uos.mystory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uos.mystory.domain.history.BlogHistory;

@Repository
public interface BlogHistoryRepository extends JpaRepository<BlogHistory, Long> {
}
