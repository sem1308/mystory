package uos.mystory.domain.history;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import uos.mystory.domain.Blog;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BlogHistory extends History{
    @Id @GeneratedValue
    @AttributeOverride(name = "id", column = @Column(name = "blog_history_id"))
    private Long id;

    @ManyToOne
    @JoinColumn(name = "blog_id")
    private Blog blog;
}
