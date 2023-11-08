package uos.mystory.dto.request.create;

public record CreateBlogDTO(
        String name,
        String url,
        String description,
        Long userId
) {}
