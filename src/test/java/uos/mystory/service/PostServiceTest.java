package uos.mystory.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import uos.mystory.domain.Blog;
import uos.mystory.domain.Category;
import uos.mystory.domain.Post;
import uos.mystory.domain.User;
import uos.mystory.domain.enums.OpenState;
import uos.mystory.domain.enums.PostType;
import uos.mystory.domain.enums.WriteType;
import uos.mystory.dto.mapping.insert.InsertBlogDTO;
import uos.mystory.dto.mapping.insert.InsertCategoryDTO;
import uos.mystory.dto.mapping.insert.InsertPostDTO;
import uos.mystory.dto.mapping.insert.InsertUserDTO;
import uos.mystory.dto.mapping.select.SelectPostInfoDTO;
import uos.mystory.dto.mapping.update.UpdatePostDTO;
import uos.mystory.exception.ResourceNotFoundException;
import uos.mystory.repository.PostRepository;
import uos.mystory.repository.condition.PostSearchCondition;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PostServiceTest{
    @Autowired
    PostService postService;
    @Autowired
    PostRepository postRepository;
    @Autowired
    UserService userService;

    @Autowired
    BlogService blogService;

    @Autowired
    CategoryService categoryService;

    User user;
    Blog blog;
    Category category;

    @BeforeEach
    public void setup() {
        Long id = userService.saveUser(InsertUserDTO.builder().userId("sem1308").userPw("1308").nickname("ddory").phoneNum("01000000000").build());
        this.user = userService.getUser(id);
        Long blogId = blogService.saveBlog(InsertBlogDTO.builder().name("Dev").url("https://han-dev.mystory.com").description("기본 블로그").user(user).build());
        this.blog = blogService.getBlog(blogId);
        Long categoryId = categoryService.saveCategory(InsertCategoryDTO.builder().name("BackEnd").blog(blog).build());
        this.category = categoryService.getCategory(categoryId);
    }

    @AfterEach
    public void clear() {
        postRepository.deleteAll();
        categoryService.deleteCategory(category.getId());
        blogService.deleteBlog(blog.getId());
        userService.deleteUser(user.getId());
    }


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

    @Test
    public void 전체_게시글_가져오기() throws Exception {
        //given
        String title = "첫 게시글";
        String url = blog.getUrl()+"/"+title.replace(" ", "-");
        InsertPostDTO postDTO = InsertPostDTO.builder().postType(PostType.POST).writeType(WriteType.BASIC).openState(OpenState.CLOSE)
                .title(title).content("그냥 끄적이는 글").url(url).titleImgPath(null).user(user).category(category).blog(blog).build();
        title = "두 번째 게시글";
        url = blog.getUrl()+"/"+title.replace(" ", "-");
        InsertPostDTO postDTO2 = InsertPostDTO.builder().postType(PostType.POST).writeType(WriteType.BASIC).openState(OpenState.CLOSE)
                .title(title).content("그냥 끄적이는 글2").url(url).titleImgPath(null).user(user).category(category).blog(blog).build();
        title = "세 번째 게시글";
        url = blog.getUrl()+"/"+title.replace(" ", "-");
        InsertPostDTO postDTO3 = InsertPostDTO.builder().postType(PostType.POST).writeType(WriteType.BASIC).openState(OpenState.CLOSE)
                .title(title).content("그냥 끄적이는 글3").url(url).titleImgPath(null).user(user).category(category).blog(blog).build();

        //when
        postService.savePost(postDTO);
        postService.savePost(postDTO2);
        postService.savePost(postDTO3);

        //when
        List<Post> posts = postService.getPosts();

        //then
        assertEquals(3, posts.size());
    }

    @Test
    public void 조건에_따른_게시글_가져오기() throws Exception {
        //given
        String title = "첫 게시글";
        String url = blog.getUrl()+"/"+title.replace(" ", "-");
        InsertPostDTO postDTO = InsertPostDTO.builder().postType(PostType.POST).writeType(WriteType.BASIC).openState(OpenState.CLOSE)
                .title(title).content("그냥 끄적이는 글").url(url).titleImgPath(null).user(user).category(category).blog(blog).build();
        title = "두 번째 게시글";
        url = blog.getUrl()+"/"+title.replace(" ", "-");
        InsertPostDTO postDTO2 = InsertPostDTO.builder().postType(PostType.POST).writeType(WriteType.BASIC).openState(OpenState.CLOSE)
                .title(title).content("그냥 끄적이는 글2").url(url).titleImgPath(null).user(user).category(category).blog(blog).build();
        title = "세 번째 게시글";
        url = blog.getUrl()+"/"+title.replace(" ", "-");
        InsertPostDTO postDTO3 = InsertPostDTO.builder().postType(PostType.POST).writeType(WriteType.BASIC).openState(OpenState.CLOSE)
                .title(title).content("그냥 끄적이는 글3").url(url).titleImgPath(null).user(user).category(category).blog(blog).build();

        //when
        postService.savePost(postDTO);
        postService.savePost(postDTO2);
        postService.savePost(postDTO3);

        PostSearchCondition postSearchCondition = new PostSearchCondition(blog.getId());

        int page = 0;
        int size = 2;
        int totalElements = 3;
        int totalPages = (int) Math.ceil((double) totalElements / size);
        List<Sort.Order> orders = new ArrayList<>();
        orders.add(Sort.Order.desc("title"));
        Sort sort = Sort.by(orders);
        Pageable pageable = PageRequest.of(page, size,sort);
        //when
        Page<SelectPostInfoDTO> postInfoDTOS = postService.getPostsgetBlogsByContidion(postSearchCondition, pageable);

        //then
        assertEquals(totalElements, postInfoDTOS.getTotalElements());
        assertEquals(totalPages, postInfoDTOS.getTotalPages());
    }

    @Test
    public void 게시글_삭제() throws Exception {
        //given
        String title = "첫 게시글";
        String url = blog.getUrl()+"/"+title.replace(" ", "-");
        InsertPostDTO postDTO = InsertPostDTO.builder().postType(PostType.POST).writeType(WriteType.BASIC).openState(OpenState.CLOSE)
                .title(title).content("그냥 끄적이는 글").url(url).titleImgPath(null).user(user).category(category).blog(blog).build();

        //when
        Long id = postService.savePost(postDTO);
        postService.deletePost(id);

        //then
        assertThrows(ResourceNotFoundException.class,
                ()->postService.getPost(id)
        );
    }

}