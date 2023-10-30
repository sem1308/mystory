package uos.mystory.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import uos.mystory.domain.Blog;
import uos.mystory.domain.User;
import uos.mystory.domain.enums.VisitedPath;
import uos.mystory.dto.mapping.insert.InsertBlogDTO;
import uos.mystory.dto.mapping.insert.InsertUserDTO;
import uos.mystory.dto.mapping.select.SelectBlogHistoryDTO;
import uos.mystory.dto.mapping.select.SelectHistoryDTO;
import uos.mystory.dto.response.BlogHistoryInfoDTO;
import uos.mystory.dto.response.HistoryInfoDTO;
import uos.mystory.repository.UserRepository;
import uos.mystory.repository.condition.BlogHistorySearchCondition;
import uos.mystory.repository.condition.HistorySearchCondition;
import uos.mystory.service.regacy.RegacyBlogHistoryService;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class BlogHistoryServiceTest{
    @Autowired
    UserService userService;

    @Autowired
    BlogService blogService;

    @Autowired
    BlogHistoryService blogHistoryService;

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
    public void 블로그_이력_가져오기() throws Exception {
        //given
        blogService.getBlogWhenVisit(blog.getId(), VisitedPath.SEARCH);
        blogService.getBlogWhenVisit(blog.getId(), VisitedPath.DEVICE);

        //when
        List<SelectHistoryDTO> histories = blogHistoryService.getHistoryInfoDTOs(blog.getId());

        //then
        // 리스트의 HistoryRecord 객체의 count 필드를 모두 더한 총합 계산
        Long totalCount = histories.stream()
                .mapToLong(SelectHistoryDTO::visits)
                .sum();
        histories.forEach(System.out::println);
        assertEquals(2, totalCount);
    }

    @Test
    public void 블로그_이력_가져오기_날짜_기준_반환() throws Exception {
        //given
        blogService.getBlogWhenVisit(blog.getId(), VisitedPath.SEARCH);
        blogService.getBlogWhenVisit(blog.getId(), VisitedPath.SEARCH);
        blogService.getBlogWhenVisit(blog.getId(), VisitedPath.DEVICE);
        blogService.getBlogWhenVisit(blog.getId(), VisitedPath.DEVICE);
        blogService.getBlogWhenVisit(blog.getId(), VisitedPath.DEVICE);

        //when
        HistoryInfoDTO histories = blogHistoryService.getHistories(blog.getId());

        //then
        System.out.println(histories);
        assertEquals(5, histories.getTotalVisits());
    }


    @Test
    public void 날짜별_블로그_이력_가져오기() throws Exception {
        //given
        blogService.getBlogWhenVisit(blog.getId(), VisitedPath.SEARCH);
        blogService.getBlogWhenVisit(blog.getId(), VisitedPath.SEARCH);
        blogService.getBlogWhenVisit(blog.getId(), VisitedPath.DEVICE);
        blogService.getBlogWhenVisit(blog.getId(), VisitedPath.DEVICE);
        blogService.getBlogWhenVisit(blog.getId(), VisitedPath.DEVICE);

        LocalDate from = LocalDate.now();
        LocalDate to = LocalDate.now();
        LocalDate to2 = LocalDate.now().minusDays(1);

        HistorySearchCondition condition = new HistorySearchCondition(blog.getId(), from, to);
        HistorySearchCondition condition2 = new HistorySearchCondition(blog.getId(), from, to2);

        //when
        HistoryInfoDTO histories = blogHistoryService.getHistories(condition);
        HistoryInfoDTO histories2 = blogHistoryService.getHistories(condition2);

        //then
        System.out.println(histories);
        assertEquals(5, histories.getTotalVisits());
        assertEquals(0, histories2.getTotalVisits());
    }
}