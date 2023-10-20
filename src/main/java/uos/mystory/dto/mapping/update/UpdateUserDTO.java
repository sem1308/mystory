package uos.mystory.dto.mapping.update;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateUserDTO {
    private Long id;

    private String userPw;

    private String nickname;

    private String phoneNum;
}
