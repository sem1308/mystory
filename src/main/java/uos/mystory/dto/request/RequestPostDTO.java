package uos.mystory.dto.request;

import uos.mystory.repository.condition.PostSearchCondition;

public record RequestPostDTO (
    PostSearchCondition condition,
    PageDTO paging
){}
