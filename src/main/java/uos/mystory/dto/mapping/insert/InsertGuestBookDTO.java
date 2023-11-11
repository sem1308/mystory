package uos.mystory.dto.mapping.insert;

import lombok.Builder;
import lombok.Getter;
import uos.mystory.domain.Blog;
import uos.mystory.dto.request.create.CreateGuestBookDTO;

@Getter
@Builder
public class InsertGuestBookDTO {
    private String content;

    private Blog blog;

    public static InsertGuestBookDTO of(CreateGuestBookDTO createGuestBookDTO, Blog blog){
        return InsertGuestBookDTO.builder()
                .content(createGuestBookDTO.content())
                .blog(blog)
                .build();
    }
}
