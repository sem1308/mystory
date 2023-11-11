package uos.mystory.dto.request.create;
import uos.mystory.domain.enums.OpenState;
import uos.mystory.domain.enums.PostType;
import uos.mystory.domain.enums.WriteType;

public record CreatePostDTO (
         PostType postType,

         WriteType writeType,

         OpenState openState,

         String title,

         String content,

         String url,

         String titleImgPath,

        /**
         * 연관 관계 매핑
         */
         Long userId,

         Long categoryId,

         Long blogId
){}
