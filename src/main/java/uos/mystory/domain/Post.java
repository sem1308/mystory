package uos.mystory.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import uos.mystory.domain.enums.OpenState;
import uos.mystory.domain.enums.PostType;
import uos.mystory.domain.enums.WriteType;

import java.time.LocalDateTime;

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

    @Column(length = 100)
    private String title;

    @Column(columnDefinition = "TEXT") // mysql의 TEXT 변수 타입 선언을 위함
    private String content;

    @Enumerated(EnumType.STRING)
    private WriteType writeType;

    @Enumerated(EnumType.STRING)
    private OpenState openState;

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
    public static Post create(PostType postType, String title, String content, WriteType writeType,
                                  OpenState openState, String url, String titleImgPath, User user, Category category, Blog blog){
        return new PostBuilder().postType(postType).title(title).content(content).writeType(writeType)
                .openState(openState).url(url).titleImgPath(titleImgPath).hearts(0).visits(0)
                .createdDateTime(LocalDateTime.now()).user(user).category(category).blog(blog).build();
    }

    //==변경 메소드==//
    public void update(PostType postType, String title, String content, WriteType writeType,
                       OpenState openState, String titleImgPath, Category category){
        this.postType = postType == null ? this.postType : postType;
        this.title = title == null ? this.title : title;
        this.content = content == null ? this.content : content;
        this.writeType = writeType == null ? this.writeType : writeType;
        this.openState = openState == null ? this.openState : openState;
        this.titleImgPath = titleImgPath == null ? this.titleImgPath : titleImgPath;
        this.category = category == null ? this.category : category;
    }

    public String toString() {
        return "[postType] : "+postType.toString()+", [title] : "+title+", [content] : "+content+", [writeType] : "+writeType.toString()
                +", [openState] : "+openState.toString()+", [url] : "+url+", [titleImgPath] : "+titleImgPath+", [user] : "+user.getUserId()
                +", [category] : "+category.getName()+", [blog] : "+blog.getName();
    }
}
