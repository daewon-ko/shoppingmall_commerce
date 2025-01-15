package shoppingmall.domainrdb.order.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Pageable;
import shoppingmall.domainrdb.order.domain.OrderStatus;


@Getter
public class OrderSearchCondition {
    private OrderStatus orderStatus;
    private Pageable pageable;


    @Builder
    public OrderSearchCondition(OrderStatus orderStatus, Pageable pageable) {
        this.orderStatus = orderStatus;
        this.pageable = pageable;
    }
}
