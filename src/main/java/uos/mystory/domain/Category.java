package uos.mystory.domain;

import jakarta.persistence.*;
import lombok.*;
import org.jetbrains.annotations.NotNull;
import uos.mystory.dto.mapping.insert.InsertCategoryDTO;
import uos.mystory.dto.mapping.update.UpdateCategoryDTO;
import uos.mystory.utils.Validator;

import java.time.LocalDateTime;
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
    public static Category create(@NotNull InsertCategoryDTO categoryDTO){
        // 연관 객체가 null인지 검사
        Validator.validateNull(categoryDTO.getBlog());
        return new CategoryBuilder().name(categoryDTO.getName()).blog(categoryDTO.getBlog()).build();
    }

    //==변경 메소드==//
    public void update(@NotNull UpdateCategoryDTO updateCategoryDTO){
        this.name = Optional.ofNullable(updateCategoryDTO.getName()).orElse(this.name);
    }

    public String toString() {
        return "[name] : "+name+", [blog] : "+blog.getName();
    }
}
