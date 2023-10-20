package uos.mystory.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import uos.mystory.domain.enums.OpenState;
import uos.mystory.domain.enums.PostType;
import uos.mystory.domain.enums.WriteType;
import uos.mystory.dto.mapping.insert.InsertPostDTO;
import uos.mystory.dto.mapping.update.UpdatePostDTO;

import java.time.LocalDateTime;
import java.util.Optional;

@Entity
@Getter
@Builder(access = AccessLevel.PROTECTED) // 생성로직은 오직 User에서만 할 수 있게
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {
    @Id @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private PostType postType;

    @Enumerated(EnumType.STRING)
    private WriteType writeType;

    @Enumerated(EnumType.STRING)
    private OpenState openState;

    @Column(length = 100)
    private String title;

    @Column(columnDefinition = "TEXT") // mysql의 TEXT 변수 타입 선언을 위함
    private String content;

    @Column(length = 2000, unique = true)
    private String url;

    @Column(length = 500)
    private String titleImgPath;

    @ColumnDefault("0")
    private Integer hearts;

    @ColumnDefault("0")
    private Integer visits;

    private LocalDateTime createdDateTime;

    /**
     * 연관 관계 매핑
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "blog_id")
    private Blog blog;


    //==생성 메소드==//
    public static Post create(InsertPostDTO insertPostDTO){
        return new PostBuilder().postType(insertPostDTO.getPostType()).title(insertPostDTO.getTitle()).content(insertPostDTO.getContent()).writeType(insertPostDTO.getWriteType())
                .openState(insertPostDTO.getOpenState()).url(insertPostDTO.getUrl()).titleImgPath(insertPostDTO.getTitleImgPath()).hearts(0).visits(0)
                .createdDateTime(LocalDateTime.now()).user(insertPostDTO.getUser()).category(insertPostDTO.getCategory()).blog(insertPostDTO.getBlog()).build();
    }

    //==변경 메소드==//
    public void update(UpdatePostDTO updatePostDTO){
        this.title = Optional.ofNullable(updatePostDTO.getTitle()).orElse(this.title);
        this.content = Optional.ofNullable(updatePostDTO.getContent()).orElse(this.content);
        this.writeType = Optional.ofNullable(updatePostDTO.getWriteType()).orElse(this.writeType);
        this.openState = Optional.ofNullable(updatePostDTO.getOpenState()).orElse(this.openState);
        this.title = Optional.ofNullable(updatePostDTO.getTitle()).orElse(this.title);
        this.category = Optional.ofNullable(updatePostDTO.getCategory()).orElse(this.category);
    }

    //==비즈니스 로직==//

    /**
     * 조회시 조회수 1 증가
     */
    public void addVisits() {
        this.visits += 1;
    }

    /**
     * 공감 버튼 눌렀을 시 공감수 1 증가
     */
    public void addHearts() {
        this.hearts += 1;
    }

    public String toString() {
        return "[postType] : "+postType.toString()+", [title] : "+title+", [content] : "+content+", [writeType] : "+writeType.toString()
                +", [openState] : "+openState.toString()+", [url] : "+url+", [titleImgPath] : "+titleImgPath+", [user] : "+user.getUserId()
                +", [category] : "+category.getName()+", [blog] : "+blog.getName();
    }
}
