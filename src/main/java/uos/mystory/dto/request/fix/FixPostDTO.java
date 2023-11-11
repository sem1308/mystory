package uos.mystory.dto.request.fix;

import uos.mystory.domain.enums.OpenState;
import uos.mystory.domain.enums.WriteType;

public record FixPostDTO(
        Long id,

        String title,

        String content,

        WriteType writeType,

        OpenState openState,

        String titleImgPath,

        /**
         * 연관 관계 매핑
         */
        Long categoryId
) {}
