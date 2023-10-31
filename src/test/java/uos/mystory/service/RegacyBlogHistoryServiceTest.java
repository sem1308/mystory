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
import uos.mystory.dto.response.BlogHistoryInfoDTO;
import uos.mystory.repository.BlogHistoryRepository;
import uos.mystory.repository.condition.BlogHistorySearchCondition;
import uos.mystory.service.regacy.RegacyBlogHistoryService;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class RegacyBlogHistoryServiceTest{
    @Autowired
    RegacyBlogHistoryService blogHistoryService;
    @Autowired
    BlogHistoryRepository blogHistoryRepository;
    @Autowired
    UserService userService;
    @Autowired
    BlogService blogService;
    @Autowired
    CategoryService categoryService;

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
        blogHistoryRepository.deleteAll();
        blogService.deleteBlog(blog.getId());
        userService.deleteUser(user.getId());
    }

    @Test
    public void 블로그_이력_가져오기() throws Exception {
        //given
        blogService.getBlogWhenVisit(blog.getId(), VisitedPath.SEARCH);
        blogService.getBlogWhenVisit(blog.getId(), VisitedPath.DEVICE);

        //when
        List<SelectBlogHistoryDTO> histories = blogHistoryService.getBlogHistoryInfoDTOs(blog.getId());

        //then
        // 리스트의 HistoryRecord 객체의 count 필드를 모두 더한 총합 계산
        Long totalCount = histories.stream()
                .mapToLong(SelectBlogHistoryDTO::visits)
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
        BlogHistoryInfoDTO histories = blogHistoryService.getBlogHistories(blog.getId());

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

        BlogHistorySearchCondition condition = new BlogHistorySearchCondition(blog.getId(), from, to);
        BlogHistorySearchCondition condition2 = new BlogHistorySearchCondition(blog.getId(), from, to2);

        //when
        BlogHistoryInfoDTO histories = blogHistoryService.getBlogHistories(condition);
        BlogHistoryInfoDTO histories2 = blogHistoryService.getBlogHistories(condition2);

        //then
        System.out.println(histories);
        assertEquals(5, histories.getTotalVisits());
        assertEquals(0, histories2.getTotalVisits());
    }
}