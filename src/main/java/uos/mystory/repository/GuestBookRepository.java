package uos.mystory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uos.mystory.domain.GuestBook;
import uos.mystory.repository.querydsl.GuestBookQueryRepository;

@Repository
public interface GuestBookRepository extends JpaRepository<GuestBook,Long>, GuestBookQueryRepository {
}
