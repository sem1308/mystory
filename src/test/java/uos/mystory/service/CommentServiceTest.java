package uos.mystory.service;

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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CommentServiceTest extends PostServiceTest{
    @Autowired
    CommentService commentService;

    Post post;

    @BeforeEach
    public void setup() {
        super.setup();
        String title = "첫 게시글";
        String url = blog.getUrl()+"/"+title.replace(" ", "-");
        InsertPostDTO postDTO = InsertPostDTO.builder().postType(PostType.POST).writeType(WriteType.BASIC).openState(OpenState.CLOSE)
                .title(title).content("그냥 끄적이는 글").url(url).titleImgPath(null).user(user).category(category).blog(blog).build();
        Long id = postService.savePost(postDTO);
        post = postService.getPost(id);
    }

    @Disabled("상속된 메서드는 실행하지 않습니다.")
    public void 게시글_생성() {}
    @Disabled("상속된 메서드는 실행하지 않습니다.")
    public void 게시글_변경() {}

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
    
}