package uos.mystory.dto.mapping.update;

import lombok.AllArgsConstructor;
import lombok.Getter;
import uos.mystory.domain.Category;
import uos.mystory.domain.User;
import uos.mystory.domain.enums.OpenState;
import uos.mystory.domain.enums.PostType;
import uos.mystory.domain.enums.WriteType;

@Getter
@AllArgsConstructor
public class UpdatePostDTO {
    private Long id;

    private PostType postType;

    private String title;

    private String content;

    private WriteType writeType;

    private OpenState openState;

    private String titleImgPath;

    /**
     * 연관 관계 매핑
     */
    private Category category;
}
