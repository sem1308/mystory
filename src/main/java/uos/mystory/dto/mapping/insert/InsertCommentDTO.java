package uos.mystory.dto.mapping.insert;

import lombok.Builder;
import lombok.Getter;
import uos.mystory.domain.Post;

@Getter
@Builder
public class InsertCommentDTO {
    private String content;

    /**
     * 연관 관계 매핑
     */
    private Post post;
}
