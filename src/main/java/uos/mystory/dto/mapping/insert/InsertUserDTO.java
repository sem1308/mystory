package uos.mystory.dto.mapping.insert;

import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class InsertUserDTO {
    String userId;
    String userPw;
    String nickname;
    String phoneNum;
}
