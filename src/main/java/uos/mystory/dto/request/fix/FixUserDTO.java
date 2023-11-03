package uos.mystory.dto.request.fix;

public record FixUserDTO (
    Long id,

    String userPw,
    String nickname,
    String phoneNum
){}
