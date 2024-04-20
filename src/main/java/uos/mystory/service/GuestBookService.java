package uos.mystory.service;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uos.mystory.domain.GuestBook;
import uos.mystory.dto.mapping.insert.InsertGuestBookDTO;
import uos.mystory.dto.mapping.select.SelectGuestBookInfoDTO;
import uos.mystory.dto.mapping.update.UpdateGuestBookDTO;
import uos.mystory.exception.ResourceNotFoundException;
import uos.mystory.exception.massage.MessageManager;
import uos.mystory.repository.GuestBookRepository;
import uos.mystory.repository.condition.GuestBookSearchCondition;
import uos.mystory.repository.querydsl.GuestBookQueryRepositoryImpl;

@Service
@RequiredArgsConstructor
@Transactional
public class GuestBookService {
    private final GuestBookRepository guestBookRepository;

    /**
     * @title 방명록 저장
     * @param insertGuestBookDTO
     * @return 방명록 번호
     */
    public Long saveGuestBook(@NotNull InsertGuestBookDTO insertGuestBookDTO) {
        GuestBook guestBook = GuestBook.create(insertGuestBookDTO);
        return guestBookRepository.save(guestBook).getId();
    }

    /**
     * @title 방명록 내용 변경
     * @param updateGuestBookDTO
     */
    public void updateGuestBook(@NotNull UpdateGuestBookDTO updateGuestBookDTO) {
        GuestBook guestBook = getGuestBook(updateGuestBookDTO.getId());
        guestBook.update(updateGuestBookDTO);
    }

    /**
     * @title 방명록 번호로 방명록 엔티티 가져오기
     * @param id
     * @return 방명록 엔티티
     */
    @Transactional(readOnly = true)
    public GuestBook getGuestBook(Long id) {
        return guestBookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(MessageManager.getMessage("error.notfound.guest_book")));
    }

    @Transactional(readOnly = true)
    public SelectGuestBookInfoDTO getGuestBookInfo(Long id) {
        return guestBookRepository.findDtoById(id).orElseThrow(() -> new ResourceNotFoundException(MessageManager.getMessage("error.notfound.guest_book")));
    }

    public Page<SelectGuestBookInfoDTO> getGuestBooksByContidion(GuestBookSearchCondition condition, Pageable pageable){
        return guestBookRepository.findAll(condition,pageable);
    }

    /**
     * @title 방명록 번호로 방명록 삭제
     * @param id
     */
    public void deleteGuestBook(Long id) {
        guestBookRepository.deleteById(id);
    }

}
