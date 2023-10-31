package uos.mystory.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import uos.mystory.domain.Blog;
import uos.mystory.domain.Category;
import uos.mystory.domain.Post;
import uos.mystory.domain.User;
import uos.mystory.domain.enums.OpenState;
import uos.mystory.domain.enums.PostType;
import uos.mystory.domain.enums.VisitedPath;
import uos.mystory.domain.enums.WriteType;
import uos.mystory.dto.mapping.insert.InsertBlogDTO;
import uos.mystory.dto.mapping.insert.InsertCategoryDTO;
import uos.mystory.dto.mapping.insert.InsertPostDTO;
import uos.mystory.dto.mapping.insert.InsertUserDTO;
import uos.mystory.dto.response.PostHistoryInfoDTO;
import uos.mystory.repository.PostHistoryRepository;
import uos.mystory.repository.condition.PostHistorySearchCondition;
import uos.mystory.repository.querydsl.regacy.RegacyPostHistoryQueryRepository;
import uos.mystory.service.regacy.RegacyPostHistoryService;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PostHistoryServiceTest{
    @Autowired
    RegacyPostHistoryService postHistoryService;
    @Autowired
    PostHistoryRepository postHistoryRepository;
    @Autowired
    PostService postService;
    @Autowired
    UserService userService;
    @Autowired
    BlogService blogService;
    @Autowired
    CategoryService categoryService;

    User user;
    Blog blog;
    Category category;

    Post post;

    @BeforeEach
    public void setup() {
        Long userId = userService.saveUser(InsertUserDTO.builder().userId("sem1308").userPw("1308").nickname("ddory").phoneNum("01000000000").build());
        this.user = userService.getUser(userId);
        Long blogId = blogService.saveBlog(InsertBlogDTO.builder().name("Dev").url("https://han-dev.mystory.com").description("기본 블로그").user(user).build());
        this.blog = blogService.getBlog(blogId);
        Long categoryId = categoryService.saveCategory(InsertCategoryDTO.builder().name("BackEnd").blog(blog).build());
        this.category = categoryService.getCategory(categoryId);
        String title = "첫 게시글";
        String url = blog.getUrl()+"/"+title.replace(" ", "-");
        InsertPostDTO postDTO = InsertPostDTO.builder().postType(PostType.POST).writeType(WriteType.BASIC).openState(OpenState.CLOSE)
                .title(title).content("그냥 끄적이는 글").url(url).titleImgPath(null).user(user).category(category).blog(blog).build();
        Long id = postService.savePost(postDTO);
        post = postService.getPost(id);
    }

    @AfterEach
    public void clear() {
        postHistoryRepository.deleteAll();
        postService.deletePost(post.getId());
        categoryService.deleteCategory(category.getId());
        blogService.deleteBlog(blog.getId());
        userService.deleteUser(user.getId());
    }

    @Test
    public void 날짜별_게시글_이력_가져오기() throws Exception {
        //given
        postService.getPostWhenVisit(post.getId(), VisitedPath.SEARCH);
        postService.getPostWhenVisit(post.getId(), VisitedPath.SEARCH);
        postService.getPostWhenVisit(post.getId(), VisitedPath.SEARCH);
        postService.getPostWhenVisit(post.getId(), VisitedPath.DEVICE);
        postService.getPostWhenVisit(post.getId(), VisitedPath.DEVICE);

        LocalDate from = LocalDate.now();
        LocalDate to = LocalDate.now();
        LocalDate to2 = LocalDate.now().minusDays(1);

        PostHistorySearchCondition condition = new PostHistorySearchCondition(post.getId(), from, to);
        PostHistorySearchCondition condition2 = new PostHistorySearchCondition(post.getId(), from, to2);

        //when
        PostHistoryInfoDTO postHistories = postHistoryService.getPostHistories(condition);
        PostHistoryInfoDTO postHistories2 = postHistoryService.getPostHistories(condition2);

        //then
        assertEquals(5, postHistories.getTotalVisits());
        assertEquals(0, postHistories2.getTotalVisits());
    }
}