package uos.mystory.dto.mapping.update;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UpdateBlogDTO {
    private Long id;

    private String name;

    private String url;

    private String description;
}
