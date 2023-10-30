package uos.mystory.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import uos.mystory.domain.enums.VisitedPath;
import uos.mystory.dto.mapping.select.SelectBlogHistoryDTO;
import uos.mystory.dto.response.BlogHistoryInfoDTO;
import uos.mystory.repository.condition.BlogHistorySearchCondition;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BlogHistoryServiceTest extends CategoryServiceTest{
    @Autowired
    BlogHistoryService blogHistoryService;

    @BeforeEach
    public void setup() {
        super.setup();
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