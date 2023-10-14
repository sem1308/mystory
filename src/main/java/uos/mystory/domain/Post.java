package uos.mystory.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import uos.mystory.domain.enums.OpenState;
import uos.mystory.domain.enums.PostType;
import uos.mystory.domain.enums.WriteType;

import java.time.LocalDateTime;

@Entity
@Getter
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

    @Column(length = 2000)
    private String url;

    @Column(length = 500)
    private String titleImgPath;

    private Integer hearts;

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
}
