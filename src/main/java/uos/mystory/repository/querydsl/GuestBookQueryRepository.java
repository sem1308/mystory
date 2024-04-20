package uos.mystory.repository.querydsl;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uos.mystory.dto.mapping.select.SelectGuestBookInfoDTO;
import uos.mystory.repository.condition.GuestBookSearchCondition;

import java.util.List;
import java.util.Optional;

public interface GuestBookQueryRepository{

    Page<SelectGuestBookInfoDTO> findAll(@NotNull GuestBookSearchCondition condition, @NotNull Pageable pageable);

    List<SelectGuestBookInfoDTO> findAll(@NotNull GuestBookSearchCondition condition);

    Optional<SelectGuestBookInfoDTO> findDtoById(Long guestBookId);
}
