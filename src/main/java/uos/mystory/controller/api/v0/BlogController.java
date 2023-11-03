package uos.mystory.controller.api.v0;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uos.mystory.domain.Blog;
import uos.mystory.dto.mapping.insert.InsertBlogDTO;
import uos.mystory.dto.mapping.select.SelectBlogInfoDTO;
import uos.mystory.dto.mapping.update.UpdateBlogDTO;
import uos.mystory.dto.request.create.CreateBlogDTO;
import uos.mystory.dto.request.fix.FixBlogDTO;
import uos.mystory.dto.response.ResponseBlogDTO;
import uos.mystory.repository.condition.BlogSearchCondition;
import uos.mystory.service.BlogService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/blogs")
public class BlogController {
    private final BlogService blogService;
    @GetMapping("/list/{user_id}")
    public ResponseEntity<List<SelectBlogInfoDTO>> getBlogs(@PathVariable("user_id") Long id){
        BlogSearchCondition blogSearchCondition = new BlogSearchCondition(id);
        List<SelectBlogInfoDTO> blogInfoDTOS = blogService.getBlogsByContidion(blogSearchCondition);
        return ResponseEntity.ok(blogInfoDTOS);
    }

    @GetMapping("/{user_id}")
    public ResponseEntity<ResponseBlogDTO> getBlog(@PathVariable("user_id") Long id){
        Blog blog = blogService.getBlog(id);
        ResponseBlogDTO blogDTO = new ResponseBlogDTO(blog.getId(), blog.getName(), blog.getUrl(), blog.getDescription(), blog.getVisits());
        return ResponseEntity.ok(blogDTO);
    }

    @PostMapping()
    public ResponseEntity<Long> createBlog(@RequestBody CreateBlogDTO blogDTO){
        InsertBlogDTO insertBlogDTO = InsertBlogDTO.builder()
                .name(blogDTO.name())
                .url(blogDTO.url())
                .description(blogDTO.description())
                .user(blogDTO.user())
                .build();
        Long id = blogService.saveBlog(insertBlogDTO);
        return ResponseEntity.ok(id);
    }

    @PutMapping()
    public ResponseEntity<String> updateBlog(@RequestBody FixBlogDTO blogDTO){
        UpdateBlogDTO updateBlogDTO = UpdateBlogDTO.builder()
                .id(blogDTO.id())
                .description(blogDTO.description())
                .url(blogDTO.url())
                .name(blogDTO.name())
                .build();
        blogService.updateBlog(updateBlogDTO);
        return ResponseEntity.ok("success");
    }

    @DeleteMapping("/{blog_id}")
    public ResponseEntity<String> deleteBlog(@PathVariable("blog_id") Long id){
        blogService.deleteBlog(id);
        return ResponseEntity.ok("success");
    }
}
