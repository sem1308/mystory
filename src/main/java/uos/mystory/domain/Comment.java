package uos.mystory.domain;

import jakarta.persistence.*;
import lombok.*;
import uos.mystory.dto.mapping.insert.InsertCommentDTO;
import uos.mystory.dto.mapping.update.UpdateCommentDTO;

import java.time.LocalDateTime;
import java.util.Optional;

@Entity
@Getter
@Builder(access = AccessLevel.PROTECTED) // 생성로직은 오직 User에서만 할 수 있게
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {

    @Id
    @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    @Column(length = 500)
    private String content;

    private LocalDateTime createdDateTime;

    /**
     * 연관 관계 매핑
     */
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    //==생성 메소드==//
    public static Comment create(InsertCommentDTO insertCommentDTO){
        return new CommentBuilder().content(insertCommentDTO.getContent()).createdDateTime(LocalDateTime.now()).post(insertCommentDTO.getPost()).build();
    }

    //==변경 메소드==//
    public void update(UpdateCommentDTO updateCommentDTO){
        this.content = Optional.ofNullable(updateCommentDTO.getContent()).orElse(this.content);
    }

    public String toString() {
        return "[content] : "+content+", [post] : "+post.getTitle();
    }
}
