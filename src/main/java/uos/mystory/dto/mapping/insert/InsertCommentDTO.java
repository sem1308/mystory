package uos.mystory.dto.mapping.insert;

import lombok.Builder;
import lombok.Getter;
import uos.mystory.domain.Post;
import uos.mystory.dto.request.create.CreateCommentDTO;

@Getter
@Builder
public class InsertCommentDTO {
    private String content;

    /**
     * 연관 관계 매핑
     */
    private Post post;

    public static InsertCommentDTO of(CreateCommentDTO createCommentDTO, Post post){
        return InsertCommentDTO.builder()
                .content(createCommentDTO.content())
                .post(post)
                .build();
    }
}
