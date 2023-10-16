package uos.mystory.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

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
    public static Comment create(String content, Post post){
        return new CommentBuilder().content(content).createdDateTime(LocalDateTime.now()).post(post).build();
    }

    //==변경 메소드==//
    public void update(String content){
        this.content = content == null ? this.content : content;
    }
}
