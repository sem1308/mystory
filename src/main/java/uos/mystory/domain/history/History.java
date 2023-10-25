package uos.mystory.domain.history;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;
import uos.mystory.domain.enums.VisitedPath;

import java.time.LocalDate;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class History {
    @Id @GeneratedValue
    private Long id;

    @ColumnDefault("0")
    protected VisitedPath path;

    protected LocalDate createdDate;

    protected History(){
        this.createdDate = LocalDate.now();
    }
}
