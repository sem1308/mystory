package uos.mystory.dto.request.create;

public record CreateUserDTO(
    String userId,
    String userPw,
    String nickname,
    String phoneNum
){}
