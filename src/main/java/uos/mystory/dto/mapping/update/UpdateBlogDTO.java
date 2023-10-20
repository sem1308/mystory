package uos.mystory.dto.mapping.update;

import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class UpdateBlogDTO {
    private Long id;

    private String name;

    private String url;

    private String description;
}
