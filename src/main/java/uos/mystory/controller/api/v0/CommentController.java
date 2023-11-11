package uos.mystory.controller.api.v0;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uos.mystory.domain.Category;
import uos.mystory.domain.Comment;
import uos.mystory.domain.Post;
import uos.mystory.dto.mapping.insert.InsertCommentDTO;
import uos.mystory.dto.mapping.select.SelectCommentInfoDTO;
import uos.mystory.dto.mapping.update.UpdateCommentDTO;
import uos.mystory.dto.request.RequestCommentDTO;
import uos.mystory.dto.request.create.CreateCommentDTO;
import uos.mystory.dto.request.fix.FixCommentDTO;
import uos.mystory.service.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v0/comments")
public class CommentController {
    private final CommentService commentService;
    private final PostService postService;

    @PostMapping("/list")
    public ResponseEntity<Page<SelectCommentInfoDTO>> getComments(@RequestBody RequestCommentDTO requestCommentDTO){
        Page<SelectCommentInfoDTO> commentInfoDTOS = commentService.getCommentsByContidion(requestCommentDTO.condition(),requestCommentDTO.paging().createPage());
        return ResponseEntity.ok(commentInfoDTOS);
    }

    @GetMapping("/{comment_id}")
    public ResponseEntity<SelectCommentInfoDTO> getComment(@PathVariable("comment_id") Long id){
        SelectCommentInfoDTO commentDTO = commentService.getCommentInfo(id);
        return ResponseEntity.ok(commentDTO);
    }

    @PostMapping()
    public ResponseEntity<Long> createComment(@RequestBody CreateCommentDTO createCommentDTO){
        Post post = postService.getPost(createCommentDTO.postId());

        InsertCommentDTO insertCommentDTO = InsertCommentDTO.of(createCommentDTO,post);
        Long id = commentService.saveComment(insertCommentDTO);
        return ResponseEntity.ok(id);
    }

    @PatchMapping()
    public ResponseEntity<String> updateComment(@RequestBody FixCommentDTO commentDTO){
        UpdateCommentDTO updateCommentDTO = UpdateCommentDTO.of(commentDTO);
        commentService.updateComment(updateCommentDTO);
        return ResponseEntity.ok("success");
    }

    @DeleteMapping("/{comment_id}")
    public ResponseEntity<String> deleteComment(@PathVariable("comment_id") Long id){
        commentService.deleteComment(id);
        return ResponseEntity.ok("success");
    }
}
