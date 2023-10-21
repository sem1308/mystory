package uos.mystory.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import uos.mystory.domain.Blog;
import uos.mystory.domain.GuestBook;
import uos.mystory.dto.mapping.insert.InsertBlogDTO;
import uos.mystory.dto.mapping.insert.InsertGuestBookDTO;
import uos.mystory.dto.mapping.update.UpdateGuestBookDTO;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class GuestBookServiceTest extends BlogServiceTest {
    @Autowired GuestBookService guestBookService;

    Blog blog;

    @BeforeEach
    public void setup() {
        super.setup();
        Long blogId = blogService.saveBlog(InsertBlogDTO.builder().name("Dev").url("https://han-dev.mystory.com").description("기본 블로그").user(user).build());
        this.blog = blogService.getBlog(blogId);
    }

    @Disabled("상속된 메서드는 실행하지 않습니다.")
    public void 블로그_생성() {}
    @Disabled("상속된 메서드는 실행하지 않습니다.")
    public void 블로그_변경() {}

    @Test
    public void 방명록_생성() throws Exception {
        //given
        String content = "첫 방명록";
        InsertGuestBookDTO insertGuestBookDTO = InsertGuestBookDTO.builder().content(content).blog(blog).build();

        //when
        Long id = guestBookService.saveGuestBook(insertGuestBookDTO);
        GuestBook guestBook = guestBookService.getGuestBook(id);

        //then
        assertEquals(guestBook.getId(), id);
        System.out.println(guestBook);
    }

    @Test
    public void 방명록_내용_변경() throws Exception {
        //given
        String content = "첫 방명록";
        InsertGuestBookDTO insertGuestBookDTO = InsertGuestBookDTO.builder().content(content).blog(blog).build();
        Long id = guestBookService.saveGuestBook(insertGuestBookDTO);

        String updatedContent = "두 번째 방명록";
        UpdateGuestBookDTO updateGuestBookDTO = UpdateGuestBookDTO.builder().id(id).content(updatedContent).build();

        //when
        guestBookService.updateGuestBook(updateGuestBookDTO);
        GuestBook guestBook = guestBookService.getGuestBook(id);

        //then
        assertEquals(guestBook.getContent(), updatedContent);
        System.out.println(guestBook);
    }
}