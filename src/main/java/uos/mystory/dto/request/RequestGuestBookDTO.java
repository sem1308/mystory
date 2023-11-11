package uos.mystory.dto.request;

import uos.mystory.repository.condition.GuestBookSearchCondition;

public record RequestGuestBookDTO (
        GuestBookSearchCondition condition,
        PageDTO paging
){}
