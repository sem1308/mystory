package uos.mystory.dto.response;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import uos.mystory.domain.enums.UserRole;

public record ResponseUserDTO(
        Long id,
        String nickname,
        Integer maxNumBlog,
        @Enumerated(EnumType.STRING)
        UserRole role
) { }
