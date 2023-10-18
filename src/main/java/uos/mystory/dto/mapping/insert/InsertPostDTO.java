package uos.mystory.dto.mapping.insert;

import lombok.AllArgsConstructor;
import lombok.Getter;
import uos.mystory.domain.Blog;
import uos.mystory.domain.Category;
import uos.mystory.domain.User;
import uos.mystory.domain.enums.OpenState;
import uos.mystory.domain.enums.PostType;
import uos.mystory.domain.enums.WriteType;

@Getter
@AllArgsConstructor
public class InsertPostDTO {
    private PostType postType;

    private String title;

    private String content;

    private WriteType writeType;

    private OpenState openState;

    private String url;

    private String titleImgPath;

    /**
     * 연관 관계 매핑
     */
    private User user;

    private Category category;

    private Blog blog;
}
