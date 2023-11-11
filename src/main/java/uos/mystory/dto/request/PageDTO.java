package uos.mystory.dto.request;

import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import uos.mystory.utils.Order;

import java.util.ArrayList;
import java.util.List;

@Data
public class PageDTO {
    int page = 0;
    int size = 15;
    List<Order> orders = new ArrayList<>();

    public Pageable createPage(){
        List<Sort.Order> orderList = orders.stream().map((order) -> new Sort.Order(order.getDirection(), order.getProperty())).toList();
        Sort sort = Sort.by(orderList);
        return PageRequest.of(page, size,sort);
    }
}
