package uos.mystory.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import uos.mystory.domain.*;
import uos.mystory.domain.enums.OpenState;
import uos.mystory.domain.enums.PostType;
import uos.mystory.domain.enums.WriteType;
import uos.mystory.dto.mapping.insert.*;
import uos.mystory.dto.mapping.update.UpdateCommentDTO;
import uos.mystory.exception.ResourceNotFoundException;
import uos.mystory.repository.CommentRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CommentServiceTest{
    @Autowired
    PostService postService;

    @Autowired
    UserService userService;

    @Autowired
    BlogService blogService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    CommentService commentService;

    @Autowired
    CommentRepository commentRepository;

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
        commentRepository.deleteAll();
        postService.deletePost(post.getId());
        categoryService.deleteCategory(category.getId());
        blogService.deleteBlog(blog.getId());
        userService.deleteUser(user.getId());
    }


    @Test
    protected void 댓글_생성() throws Exception {
        //given
        String content = "정말 유익한 글이에요!";
        InsertCommentDTO insertCommentDTO = InsertCommentDTO.builder().content(content).post(post).build();

        //when
        Long id = commentService.saveComment(insertCommentDTO);
        Comment comment = commentService.getComment(id);

        //then
        assertEquals(comment.getId(),id);
        System.out.println(comment);
    }
    @Test
    protected void 댓글_변경() throws Exception {
        //given
        String content = "정말 유익한 글이에요!";
        InsertCommentDTO insertCommentDTO = InsertCommentDTO.builder().content(content).post(post).build();
        Long id = commentService.saveComment(insertCommentDTO);
        Comment comment = commentService.getComment(id);

        //when
        String updatedContend = "이게 유익한 글인가?";
        UpdateCommentDTO updateCommentDTO = UpdateCommentDTO.builder().content(updatedContend).build();
        comment.update(updateCommentDTO);
        Comment updatedComment = commentService.getComment(id);

        //then
        assertEquals(updatedComment.getContent(),updatedContend);
        System.out.println(comment);
    }

    @Test
    public void 댓글_삭제() throws Exception {
        //given
        String content = "정말 유익한 글이에요!";
        InsertCommentDTO insertCommentDTO = InsertCommentDTO.builder().content(content).post(post).build();

        //when
        Long id = commentService.saveComment(insertCommentDTO);
        commentService.deleteComment(id);

        //then
        assertThrows(ResourceNotFoundException.class, () -> commentService.getComment(id));
    }
    
}