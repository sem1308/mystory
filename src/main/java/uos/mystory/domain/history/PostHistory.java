package uos.mystory.domain.history;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import uos.mystory.domain.Post;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostHistory extends History{
    @Id @GeneratedValue
    @AttributeOverride(name = "id", column = @Column(name = "post_history_id"))
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

}
