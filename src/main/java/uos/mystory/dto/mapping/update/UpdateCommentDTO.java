package uos.mystory.dto.mapping.update;

import lombok.Builder;
import lombok.Getter;
import uos.mystory.dto.request.create.CreateCommentDTO;
import uos.mystory.dto.request.fix.FixCommentDTO;

@Getter
@Builder
public class UpdateCommentDTO {
    Long id;
    String content;

    public static UpdateCommentDTO of(FixCommentDTO fixCommentDTO){
        return UpdateCommentDTO.builder()
                .id(fixCommentDTO.id())
                .content(fixCommentDTO.content())
                .build();
    }
}
