package uos.mystory.domain.history;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class History {
    @Id @GeneratedValue
    private Long id;

    private Integer visits;

    private LocalDateTime createdDateTime;
}
