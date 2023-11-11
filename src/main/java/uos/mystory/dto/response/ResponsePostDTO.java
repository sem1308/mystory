package uos.mystory.dto.response;

import lombok.Getter;
import uos.mystory.domain.Category;
import uos.mystory.domain.Post;
import uos.mystory.domain.enums.OpenState;
import uos.mystory.domain.enums.PostType;
import uos.mystory.domain.enums.WriteType;
import uos.mystory.dto.mapping.select.SelectCategoryInfoDTO;

import java.time.LocalDateTime;

@Getter
public class ResponsePostDTO {
    private Long postId;
    private PostType postType;
    private WriteType writeType;
    private OpenState openState;
    private String title;
    private String content;
    private String url;
    private String titleImgPath;
    private Integer hearts;
    private Integer visits;
    private LocalDateTime createdDateTime;
    private SelectCategoryInfoDTO category;

    public static ResponsePostDTO of(Post post){
        ResponsePostDTO postDTO = new ResponsePostDTO();
        postDTO.postId = post.getId();
        postDTO.postType = post.getPostType();
        postDTO.writeType = post.getWriteType();
        postDTO.openState = post.getOpenState();
        postDTO.title = post.getTitle();
        postDTO.content = post.getContent();
        postDTO.url = post.getUrl();
        postDTO.titleImgPath = post.getTitleImgPath();
        postDTO.hearts = post.getHearts();
        postDTO.visits = post.getVisits();
        postDTO.createdDateTime = post.getCreatedDateTime();
        postDTO.category = new SelectCategoryInfoDTO(post.getCategory().getId(),post.getCategory().getName());
        return postDTO;
    }
}
