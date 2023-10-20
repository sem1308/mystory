package uos.mystory.dto.mapping.update;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateCommentDTO {
    Long id;
    String content;
}
