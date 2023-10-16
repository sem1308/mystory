package uos.mystory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uos.mystory.domain.history.PostHistory;

@Repository
public interface PostHistoryRepository extends JpaRepository<PostHistory, Long> {
}
