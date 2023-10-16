package uos.mystory.domain;

import jakarta.persistence.*;
import lombok.*;
import uos.mystory.domain.enums.UserRole;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder(access = AccessLevel.PROTECTED) // 생성로직은 오직 User에서만 할 수 있게
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GuestBook {
    @Id
    @GeneratedValue
    @Column(name = "guest_book_id")
    private Long id;

    @Column(length = 500)
    private String content;

    private LocalDateTime createdDateTime;

    /**
     * 연관 관계 매핑
     */
    @ManyToOne
    @JoinColumn(name = "blog_id")
    private Blog blog;

    //==생성 메소드==//
    public static GuestBook create(String content, Blog blog){
        return new GuestBookBuilder().content(content).createdDateTime(LocalDateTime.now()).blog(blog).build();
    }

    //==변경 메소드==//
    public void update(String content){
        this.content = content == null ? this.content : content;
    }
}
