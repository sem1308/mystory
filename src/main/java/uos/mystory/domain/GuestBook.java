package uos.mystory.domain;

import jakarta.persistence.*;
import lombok.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.util.Assert;
import uos.mystory.dto.mapping.insert.InsertGuestBookDTO;
import uos.mystory.dto.mapping.update.UpdateGuestBookDTO;
import uos.mystory.exception.massage.MessageManager;

import java.time.LocalDateTime;
import java.util.Optional;

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
    @JoinColumn(name = "blog_id", nullable = false)
    private Blog blog;

    //==생성 메소드==//
    public static GuestBook create(@NotNull InsertGuestBookDTO insertGuestBookDTO){
        Assert.notNull(insertGuestBookDTO.getBlog(), MessageManager.getMessage("error.null",Blog.class));

        return new GuestBookBuilder().content(insertGuestBookDTO.getContent()).createdDateTime(LocalDateTime.now()).blog(insertGuestBookDTO.getBlog()).build();
    }

    //==변경 메소드==//
    public void update(@NotNull UpdateGuestBookDTO updateGuestBookDTO){
        this.content = Optional.ofNullable(updateGuestBookDTO.getContent()).orElse(this.content);
    }

    public String toString() {
        return "[content] : "+content+", [blog] : " + blog.getName();
    }
}
