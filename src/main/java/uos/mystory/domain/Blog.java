package uos.mystory.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

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

    @Column(length = 2000, unique = true)
    private String url;

    @Column(length = 500)
    private String description;

    @ColumnDefault("0")
    private Integer visits;

    /**
     * 연관 관계 매핑
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    //==생성 메소드==//
    public static Blog create(String name, String url, String description, User user){
        return new BlogBuilder().name(name).url(url).description(description).visits(0).user(user).build();
    }

    //==변경 메소드==//
    public void update(String name, String url, String description){
        this.name = name == null ? this.name : name;
        this.url = url == null ? this.url : url;
        this.description = description == null ? this.description : description;
    }

    public String toString() {
        return "[name] : "+name+", [url] : "+url+", [description] : "+description+", [visits] : "+visits;
    }
}
