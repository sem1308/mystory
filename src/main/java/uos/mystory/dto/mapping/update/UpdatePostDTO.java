package uos.mystory.dto.mapping.update;

import lombok.Builder;
import lombok.Getter;
import uos.mystory.domain.Category;
import uos.mystory.domain.enums.OpenState;
import uos.mystory.domain.enums.WriteType;
import uos.mystory.dto.request.fix.FixPostDTO;

@Getter
@Builder
public class UpdatePostDTO {
    private Long id;

    private String title;

    private String content;

    private WriteType writeType;

    private OpenState openState;

    private String titleImgPath;

    /**
     * 연관 관계 매핑
     */
    private Category category;

    public static UpdatePostDTO of(FixPostDTO fixPostDTO, Category category){
        return UpdatePostDTO.builder()
                .id(fixPostDTO.id())
                .title(fixPostDTO.title())
                .content(fixPostDTO.content())
                .writeType(fixPostDTO.writeType())
                .openState(fixPostDTO.openState())
                .titleImgPath(fixPostDTO.titleImgPath())
                .category(category).build();
    }
}
