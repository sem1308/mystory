package uos.mystory.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import uos.mystory.domain.Blog;
import uos.mystory.domain.GuestBook;
import uos.mystory.domain.User;
import uos.mystory.dto.mapping.insert.InsertBlogDTO;
import uos.mystory.dto.mapping.insert.InsertGuestBookDTO;
import uos.mystory.dto.mapping.insert.InsertUserDTO;
import uos.mystory.dto.mapping.update.UpdateGuestBookDTO;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class GuestBookServiceTest{
    @Autowired
    GuestBookService guestBookService;
    @Autowired
    BlogService blogService;
    @Autowired
    UserService userService;
    User user;
    Blog blog;

    @BeforeEach
    public void setup() {
        Long id = userService.saveUser(InsertUserDTO.builder().userId("sem1308").userPw("1308").nickname("ddory").phoneNum("01000000000").build());
        this.user = userService.getUser(id);
        Long blogId = blogService.saveBlog(InsertBlogDTO.builder().name("Dev").url("https://han-dev.mystory.com").description("기본 블로그").user(user).build());
        this.blog = blogService.getBlog(blogId);
    }

    @AfterEach
    public void clear() {
        userService.deleteUser(user.getId());
        blogService.deleteBlog(blog.getId());
    }

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