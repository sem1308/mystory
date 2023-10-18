package uos.mystory.dto.mapping.update;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateUserDTO {
    private Long id;

    private String userPw;

    private String nickname;

    private String phoneNum;
}
