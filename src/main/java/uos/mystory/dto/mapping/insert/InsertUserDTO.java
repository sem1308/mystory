package uos.mystory.dto.mapping.insert;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class InsertUserDTO {
    String userId;
    String userPw;
    String nickname;
    String phoneNum;
}
