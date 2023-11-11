package uos.mystory.dto.response;

import lombok.Getter;
import uos.mystory.domain.Comment;

import java.time.LocalDateTime;

@Getter
public class ResponseCommentDTO {
    private Long id;
    private String content;
    private LocalDateTime createdDateTime;

    public static ResponseCommentDTO of(Comment comment){
        ResponseCommentDTO responseCommentDTO = new ResponseCommentDTO();
        responseCommentDTO.id = comment.getId();
        responseCommentDTO.content = comment.getContent();
        responseCommentDTO.createdDateTime = comment.getCreatedDateTime();
        return responseCommentDTO;
    }
}
