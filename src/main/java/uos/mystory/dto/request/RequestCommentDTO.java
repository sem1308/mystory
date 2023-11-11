package uos.mystory.dto.request;

import uos.mystory.repository.condition.CommentSearchCondition;

public record RequestCommentDTO (
    CommentSearchCondition condition,
    PageDTO paging
){}
