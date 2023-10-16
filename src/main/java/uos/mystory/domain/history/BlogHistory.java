package uos.mystory.domain.history;

import jakarta.persistence.*;
import lombok.*;
import uos.mystory.domain.Blog;

@Entity
@Getter
@Builder(access = AccessLevel.PROTECTED) // 생성로직은 오직 User에서만 할 수 있게
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BlogHistory extends History{
    @Id @GeneratedValue
    @AttributeOverride(name = "id", column = @Column(name = "blog_history_id"))
    private Long id;

    @ManyToOne
    @JoinColumn(name = "blog_id")
    private Blog blog;

    //==생성 메소드==//
    public static BlogHistory create(Blog blog){
        return new BlogHistoryBuilder().blog(blog).build();
    }
}
