package uos.mystory.domain;

import jakarta.persistence.*;
import lombok.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.util.Assert;
import uos.mystory.dto.mapping.insert.InsertCategoryDTO;
import uos.mystory.dto.mapping.update.UpdateCategoryDTO;
import uos.mystory.exception.massage.MessageManager;

import java.util.Optional;

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
    @JoinColumn(name = "blog_id", nullable = false)
    private Blog blog;

    //==생성 메소드==//
    public static Category create(@NotNull InsertCategoryDTO insertCategoryDTO){
        // 연관 객체가 null인지 검사
        Assert.notNull(insertCategoryDTO.getBlog(), MessageManager.getMessage("error.null",Blog.class));

        return new CategoryBuilder().name(insertCategoryDTO.getName()).blog(insertCategoryDTO.getBlog()).build();
    }

    //==변경 메소드==//
    public void update(@NotNull UpdateCategoryDTO updateCategoryDTO){
        this.name = Optional.ofNullable(updateCategoryDTO.getName()).orElse(this.name);
    }

    public String toString() {
        return "[name] : "+name+", [blog] : "+blog.getName();
    }
}
