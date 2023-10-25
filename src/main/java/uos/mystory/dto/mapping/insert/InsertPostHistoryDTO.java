package uos.mystory.dto.mapping.insert;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;
import uos.mystory.domain.Post;
import uos.mystory.domain.enums.VisitedPath;

@Getter
@Builder
public class InsertPostHistoryDTO {
    Post post;
    @Enumerated(EnumType.STRING)
    VisitedPath path;
}
