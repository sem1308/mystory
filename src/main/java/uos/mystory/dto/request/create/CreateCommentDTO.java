package uos.mystory.dto.request.create;

public record CreateCommentDTO(
        String content,
        Long postId
) {}
