package uos.mystory.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import uos.mystory.domain.Category;
import uos.mystory.domain.Post;
import uos.mystory.domain.enums.OpenState;
import uos.mystory.domain.enums.PostType;
import uos.mystory.domain.enums.WriteType;
import uos.mystory.dto.mapping.insert.InsertCategoryDTO;
import uos.mystory.dto.mapping.insert.InsertPostDTO;
import uos.mystory.dto.mapping.update.UpdatePostDTO;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PostServiceTest extends CategoryServiceTest{
    @Autowired
    protected PostService postService;

    protected Category category;

    @BeforeEach
    public void setup() {
        super.setup();
        Long categoryId = categoryService.saveCategory(InsertCategoryDTO.builder().name("BackEnd").blog(blog).build());
        this.category = categoryService.getCategory(categoryId);
    }

    @Disabled("상속된 메서드는 실행하지 않습니다.")
    public void 카테고리_생성() {}

    @Test
    protected void 게시글_생성() throws Exception {
        //given
        String title = "첫 게시글";
        String url = blog.getUrl()+"/"+title.replace(" ", "-");
        InsertPostDTO postDTO = InsertPostDTO.builder().postType(PostType.POST).writeType(WriteType.BASIC).openState(OpenState.CLOSE)
                .title(title).content("그냥 끄적이는 글").url(url).titleImgPath(null).user(user).category(category).blog(blog).build();

        //when
        Long id = postService.savePost(postDTO);
        Post post = postService.getPost(id);

        //then
        assertEquals(post.getId(),id);
        System.out.println(post);
    }

    @Test
    protected void 게시글_변경() throws Exception {
        //given
        String title = "첫 게시글";
        String url = blog.getUrl()+"/"+title.replace(" ", "-");
        PostType postType = PostType.POST;
        WriteType writeType = WriteType.BASIC;
        OpenState openState = OpenState.CLOSE;
        InsertPostDTO postDTO = InsertPostDTO.builder().postType(postType).writeType(writeType).openState(openState)
                .title(title).content("그냥 끄적이는 글").url(url).titleImgPath(null).user(user).category(category).blog(blog).build();
        Long id = postService.savePost(postDTO);

        //when
        String updatedTitle = "첫 게시글 (수정)";
        String updatedContent = "아무 내용";
        WriteType updatedWriteType = WriteType.BASIC;
        OpenState updatedOpenState = OpenState.CLOSE;
        UpdatePostDTO updatePostDTO = UpdatePostDTO.builder().id(id).title(updatedTitle).content(updatedContent)
                .writeType(updatedWriteType).openState(updatedOpenState).titleImgPath(null).category(null).build();

        postService.updatePost(updatePostDTO);

        //then
        Post updatedPost = postService.getPost(id);
        assertEquals(updatedPost.getTitle(),updatedTitle);
        assertEquals(updatedPost.getContent(),updatedContent);
        assertEquals(updatedPost.getWriteType(),updatedWriteType);
        assertEquals(updatedPost.getOpenState(),updatedOpenState);
        System.out.println(updatedPost);
    }

}