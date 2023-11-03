package uos.mystory.dto.request.create;

import uos.mystory.domain.User;

public record CreateBlogDTO(
        String name,
        String url,
        String description,
        User user
) {}
