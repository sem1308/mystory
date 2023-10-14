package uos.mystory.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
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
}
