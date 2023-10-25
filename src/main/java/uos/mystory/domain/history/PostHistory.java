package uos.mystory.domain.history;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import uos.mystory.domain.Post;
import uos.mystory.dto.mapping.insert.InsertPostHistoryDTO;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 생성로직은 오직 User에서만 할 수 있게
public class PostHistory extends History{
    @Id @GeneratedValue
    @AttributeOverride(name = "id", column = @Column(name = "post_history_id"))
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    protected PostHistory(InsertPostHistoryDTO insertPostHistoryDTO){
        super();
        this.post = insertPostHistoryDTO.getPost();
        this.path = insertPostHistoryDTO.getPath();
    }

    //==생성 메소드==//
    public static PostHistory create(InsertPostHistoryDTO insertPostHistoryDTO){
        return new PostHistory(insertPostHistoryDTO);
    }
}
