package uos.mystory.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder(access = AccessLevel.PROTECTED) // 생성로직은 오직 User에서만 할 수 있게
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {
    @Id @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    @Column(length = 100)
    private String name;

    /**
     * 연관 관계 매핑
     */
    @ManyToOne
    @JoinColumn(name = "blog_id")
    private Blog blog;

    //==생성 메소드==//
    public static Category create(String name, Blog blog){
        return new CategoryBuilder().name(name).blog(blog).build();
    }

    //==변경 메소드==//
    public void update(String name){
        this.name = name == null ? this.name : name;
    }
}
