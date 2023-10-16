package uos.mystory.domain.history;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class History {
    @Id @GeneratedValue
    private Long id;

    @ColumnDefault("0")
    protected Integer visits;

    protected LocalDateTime createdDateTime;
}
