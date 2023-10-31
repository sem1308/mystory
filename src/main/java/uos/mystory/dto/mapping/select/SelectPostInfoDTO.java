package uos.mystory.dto.mapping.select;

import com.querydsl.core.annotations.QueryProjection;
import uos.mystory.domain.Category;
import uos.mystory.domain.enums.OpenState;
import uos.mystory.domain.enums.PostType;
import uos.mystory.domain.enums.WriteType;

import java.time.LocalDateTime;

public record SelectPostInfoDTO(
        Long postId,
        PostType postType,
        WriteType writeType,
        OpenState OpenState,
        String title,
        String content,
        String url,
        String titleImgPath,
        Integer hearts,
        Integer visits,
        LocalDateTime createdDateTime,
        Category category
) {
    @QueryProjection
    public SelectPostInfoDTO {}
}
