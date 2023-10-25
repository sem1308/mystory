package uos.mystory.domain.history;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import uos.mystory.domain.Blog;
import uos.mystory.dto.mapping.insert.InsertBlogHistoryDTO;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BlogHistory extends History {
    @Id @GeneratedValue
    @AttributeOverride(name = "id", column = @Column(name = "blog_history_id"))
    private Long id;

    @ManyToOne
    @JoinColumn(name = "blog_id")
    private Blog blog;

    protected BlogHistory(InsertBlogHistoryDTO insertBlogHistoryDTO){
        super();
        this.blog = insertBlogHistoryDTO.getBlog();
        this.path = insertBlogHistoryDTO.getPath();
    }

    //==생성 메소드==//
    public static BlogHistory create(InsertBlogHistoryDTO insertBlogHistoryDTO){
        return new BlogHistory(insertBlogHistoryDTO);
    }
}
