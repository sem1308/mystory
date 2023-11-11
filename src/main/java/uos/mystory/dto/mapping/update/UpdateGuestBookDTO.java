package uos.mystory.dto.mapping.update;

import lombok.Builder;
import lombok.Getter;
import uos.mystory.dto.request.fix.FixGuestBookDTO;

@Getter
@Builder
public class UpdateGuestBookDTO {
    Long id;
    String content;

    public static UpdateGuestBookDTO of(FixGuestBookDTO fixGuestBookDTO){
        return UpdateGuestBookDTO.builder()
                .id(fixGuestBookDTO.id())
                .content(fixGuestBookDTO.content())
                .build();
    }
}
