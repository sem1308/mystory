package uos.mystory.controller.api.v0;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uos.mystory.domain.Blog;
import uos.mystory.domain.Category;
import uos.mystory.domain.User;
import uos.mystory.domain.enums.VisitedPath;
import uos.mystory.dto.mapping.insert.InsertPostDTO;
import uos.mystory.dto.mapping.select.SelectPostInfoDTO;
import uos.mystory.dto.mapping.update.UpdatePostDTO;
import uos.mystory.dto.request.create.CreatePostDTO;
import uos.mystory.dto.request.RequestPostDTO;
import uos.mystory.dto.request.fix.FixPostDTO;
import uos.mystory.dto.response.ResponsePostDTO;
import uos.mystory.service.BlogService;
import uos.mystory.service.CategoryService;
import uos.mystory.service.PostService;
import uos.mystory.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v0/posts")
public class PostController {
    private final PostService postService;
    private final UserService userService;
    private final BlogService blogService;
    private final CategoryService categoryService;

    @PostMapping("/list")
    public ResponseEntity<Page<SelectPostInfoDTO>> getPosts(@RequestBody RequestPostDTO requestPostDTO){
        Page<SelectPostInfoDTO> postInfoDTOS = postService.getPostsByContidion(requestPostDTO.condition(),requestPostDTO.paging().createPage());
        return ResponseEntity.ok(postInfoDTOS);
    }

    @GetMapping("/{post_id}")
    public ResponseEntity<ResponsePostDTO> getPost(@PathVariable("post_id") Long id){
        ResponsePostDTO postDTO = postService.getPostWhenVisit(id, VisitedPath.SEARCH);
        return ResponseEntity.ok(postDTO);
    }

    @PostMapping()
    public ResponseEntity<Long> createPost(@RequestBody CreatePostDTO createPostDTO){
        User user = userService.getUser(createPostDTO.userId());
        Blog blog = blogService.getBlog(createPostDTO.blogId());
        Category category = categoryService.getCategory(createPostDTO.categoryId());

        InsertPostDTO insertPostDTO = InsertPostDTO.of(createPostDTO,user,blog,category);
        Long id = postService.savePost(insertPostDTO);
        return ResponseEntity.ok(id);
    }

    @PatchMapping()
    public ResponseEntity<String> updatePost(@RequestBody FixPostDTO postDTO){
        Category category = categoryService.getCategory(postDTO.id());
        UpdatePostDTO updatePostDTO = UpdatePostDTO.of(postDTO, category);
        postService.updatePost(updatePostDTO);
        return ResponseEntity.ok("success");
    }

    @DeleteMapping("/{post_id}")
    public ResponseEntity<String> deletePost(@PathVariable("post_id") Long id){
        postService.deletePost(id);
        return ResponseEntity.ok("success");
    }
}
