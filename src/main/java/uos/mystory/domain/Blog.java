package uos.mystory.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder(access = AccessLevel.PROTECTED) // 생성로직은 오직 User에서만 할 수 있게
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Blog {
    @Id @GeneratedValue
    @Column(name = "blog_id")
    private Long id;

    @Column(length = 100)
    private String name;

    @Column(length = 2000)
    private String url;

    private Integer visits;

    /**
     * 연관 관계 매핑
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    //==생성 메소드==//
    public static Blog create(String name, String url, User user){
        return new BlogBuilder().name(name).url(url).user(user).build();
    }

    //==변경 메소드==//
    public void update(String name, String url){
        this.name = name == null ? this.name : name;
        this.url = url == null ? this.url : url;
    }
}
