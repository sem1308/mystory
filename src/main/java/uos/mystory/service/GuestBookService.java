package uos.mystory.service;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import uos.mystory.domain.GuestBook;
import uos.mystory.dto.mapping.insert.InsertGuestBookDTO;
import uos.mystory.dto.mapping.update.UpdateGuestBookDTO;
import uos.mystory.exception.ResourceNotFoundException;
import uos.mystory.exception.massage.MessageManager;
import uos.mystory.repository.GuestBookRepository;

@Service
@RequiredArgsConstructor
public class GuestBookService {
    private final GuestBookRepository guestBookRepository;

    /**
     * @Title 방명록 저장
     * @param insertGuestBookDTO
     * @return 방명록 번호
     */
    public Long saveGuestBook(@NotNull InsertGuestBookDTO insertGuestBookDTO) {
        GuestBook guestBook = GuestBook.create(insertGuestBookDTO);
        return guestBookRepository.save(guestBook).getId();
    }

    /**
     * @Title 방명록 내용 변경
     * @param updateGuestBookDTO
     */
    public void updateGuestBook(@NotNull UpdateGuestBookDTO updateGuestBookDTO) {
        GuestBook guestBook = getGuestBook(updateGuestBookDTO.getId());
        guestBook.update(updateGuestBookDTO);
    }

    /**
     * @Title 방명록 번호로 방명록 엔티티 가져오기
     * @param id
     * @return 방명록 엔티티
     */
    public GuestBook getGuestBook(Long id) {
        return guestBookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(MessageManager.getMessage("error.notfound.guest_book")));
    }

}
