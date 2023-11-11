package uos.mystory.utils;

import lombok.Data;
import org.springframework.data.domain.Sort.Direction;

@Data
public class Order {
    Direction direction;
    String property;
}
