package uos.mystory.dto.response;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import uos.mystory.domain.enums.UserRole;

public record ResponseBlogDTO(
        Long id,
        String name,
        String url,
        String description,
        Integer visits
) { }
