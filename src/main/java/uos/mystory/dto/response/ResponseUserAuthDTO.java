package uos.mystory.dto.response;

public record ResponseUserAuthDTO(
        Long id,
        String nickname,
        Integer maxNumBlog
) { }
