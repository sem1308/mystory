package uos.mystory.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
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
}
