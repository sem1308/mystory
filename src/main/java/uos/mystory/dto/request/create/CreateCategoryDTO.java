package uos.mystory.dto.request.create;

import uos.mystory.domain.Blog;

public record CreateCategoryDTO (
    String name,

    Long blogId
){}
