package uos.mystory.dto.mapping.insert;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;
import uos.mystory.domain.Blog;
import uos.mystory.domain.enums.VisitedPath;

@Getter
@Builder
public class InsertBlogHistoryDTO {
    Blog blog;
    @Enumerated(EnumType.STRING)
    VisitedPath path;
}
