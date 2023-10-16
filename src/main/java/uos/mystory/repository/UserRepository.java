package uos.mystory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uos.mystory.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
