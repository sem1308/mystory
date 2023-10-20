package uos.mystory.dto.mapping.insert;

import lombok.Builder;
import lombok.Getter;
import uos.mystory.domain.User;


@Getter
@Builder
public class InsertBlogDTO {
    private String name;

    private String url;

    private String description;

    private User user;
}
