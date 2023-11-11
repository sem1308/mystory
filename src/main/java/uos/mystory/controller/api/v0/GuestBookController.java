package uos.mystory.controller.api.v0;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uos.mystory.domain.Blog;
import uos.mystory.dto.mapping.insert.InsertGuestBookDTO;
import uos.mystory.dto.mapping.select.SelectGuestBookInfoDTO;
import uos.mystory.dto.mapping.update.UpdateGuestBookDTO;
import uos.mystory.dto.request.RequestGuestBookDTO;
import uos.mystory.dto.request.create.CreateGuestBookDTO;
import uos.mystory.dto.request.fix.FixGuestBookDTO;
import uos.mystory.service.BlogService;
import uos.mystory.service.GuestBookService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v0/guest_books")
public class GuestBookController {
    private final GuestBookService guestBookService;
    private final BlogService blogService;

    @PostMapping("/list")
    public ResponseEntity<Page<SelectGuestBookInfoDTO>> getGuestBooks(@RequestBody RequestGuestBookDTO requestGuestBookDTO){
        Page<SelectGuestBookInfoDTO> guestBookInfoDTOS = guestBookService.getGuestBooksByContidion(requestGuestBookDTO.condition(),requestGuestBookDTO.paging().createPage());
        return ResponseEntity.ok(guestBookInfoDTOS);
    }

    @GetMapping("/{guestBook_id}")
    public ResponseEntity<SelectGuestBookInfoDTO> getGuestBook(@PathVariable("guestBook_id") Long id){
        SelectGuestBookInfoDTO guestBookDTO = guestBookService.getGuestBookInfo(id);
        return ResponseEntity.ok(guestBookDTO);
    }

    @PostMapping()
    public ResponseEntity<Long> createGuestBook(@RequestBody CreateGuestBookDTO createGuestBookDTO){
        Blog blog = blogService.getBlog(createGuestBookDTO.blogId());

        InsertGuestBookDTO insertGuestBookDTO = InsertGuestBookDTO.of(createGuestBookDTO,blog);
        Long id = guestBookService.saveGuestBook(insertGuestBookDTO);
        return ResponseEntity.ok(id);
    }

    @PatchMapping()
    public ResponseEntity<String> updateGuestBook(@RequestBody FixGuestBookDTO guestBookDTO){
        UpdateGuestBookDTO updateGuestBookDTO = UpdateGuestBookDTO.of(guestBookDTO);
        guestBookService.updateGuestBook(updateGuestBookDTO);
        return ResponseEntity.ok("success");
    }

    @DeleteMapping("/{guestBook_id}")
    public ResponseEntity<String> deleteGuestBook(@PathVariable("guestBook_id") Long id){
        guestBookService.deleteGuestBook(id);
        return ResponseEntity.ok("success");
    }
}
