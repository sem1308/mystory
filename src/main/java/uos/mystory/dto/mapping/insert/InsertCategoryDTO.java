package uos.mystory.dto.mapping.insert;

import lombok.AllArgsConstructor;
import lombok.Getter;
import uos.mystory.domain.Blog;

@Getter
@AllArgsConstructor
public class InsertCategoryDTO {
    private String name;

    /**
     * 연관 관계 매핑
     */
    private Blog blog;
}
