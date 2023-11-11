package uos.mystory.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.jetbrains.annotations.NotNull;
import org.springframework.util.Assert;
import uos.mystory.dto.mapping.insert.InsertBlogDTO;
import uos.mystory.dto.mapping.update.UpdateBlogDTO;
import uos.mystory.exception.MismatchException;
import uos.mystory.exception.massage.MessageManager;

import java.time.LocalDateTime;
import java.util.Optional;

@Entity
@Getter
@Builder(access = AccessLevel.PROTECTED) // 생성로직은 오직 User에서만 할 수 있게
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Blog {
    @Id @GeneratedValue
    @Column(name = "blog_id")
    private Long id;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 2000, unique = true, nullable = false)
    private String url;

    @Column(length = 500)
    private String description;

    @ColumnDefault("0")
    private Integer visits;

    private LocalDateTime createdDateTime;

    /**
     * 연관 관계 매핑
     */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    //==생성 메소드==//
    public static Blog create(InsertBlogDTO insertBlogDTO){
        Assert.notNull(insertBlogDTO.getUser(), MessageManager.getMessage("error.null",User.class));

        return new BlogBuilder().name(insertBlogDTO.getName()).url(insertBlogDTO.getUrl())
                .description(insertBlogDTO.getDescription()).visits(0).createdDateTime(LocalDateTime.now()).user(insertBlogDTO.getUser()).build();
    }

    //==변경 메소드==//
    public void update(UpdateBlogDTO updateBlogDTO){
        this.name = Optional.ofNullable(updateBlogDTO.getName()).orElse(this.name);
        this.url = Optional.ofNullable(updateBlogDTO.getUrl()).orElse(this.url);
        this.description = Optional.ofNullable(updateBlogDTO.getDescription()).orElse(this.description);
    }

    //==비즈니스 로직==//

    /**
     * 조회수 1 증가
     */
    public void addVisits() {
        this.visits += 1;
    }

    //==검증 로직==//
    /**
     * 글쓴이 와 블로그 생성자 일치 확인
     */
    public void validateOwner(@NotNull User user) {
        if (!this.user.equals(user)) {
            throw new MismatchException(MessageManager.getMessage("error.mismatch.blog.user"));
        }
    }

    public String toString() {
        return "[name] : "+name+", [url] : "+url+", [description] : "+description+", [visits] : "+visits;
    }
}
