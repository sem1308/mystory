package uos.mystory.dto.mapping.insert;

import lombok.AllArgsConstructor;
import lombok.Getter;
import uos.mystory.domain.User;

@AllArgsConstructor
@Getter
public class InsertBlogDTO {
    private String name;

    private String url;

    private String description;

    private User user;
}
