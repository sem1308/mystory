package uos.mystory.dto.mapping.insert;

import lombok.Builder;
import lombok.Getter;
import uos.mystory.domain.Blog;

@Getter
@Builder
public class InsertGuestBookDTO {
    private String content;

    private Blog blog;
}
