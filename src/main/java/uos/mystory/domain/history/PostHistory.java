package uos.mystory.domain.history;

import jakarta.persistence.*;
import lombok.*;
import uos.mystory.domain.Post;

@Entity
@Getter
@Builder(access = AccessLevel.PROTECTED) // 생성로직은 오직 User에서만 할 수 있게
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostHistory extends History{
    @Id @GeneratedValue
    @AttributeOverride(name = "id", column = @Column(name = "post_history_id"))
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    //==생성 메소드==//
    public static PostHistory create(Post post){
        return new PostHistoryBuilder().post(post).build();
    }
}
