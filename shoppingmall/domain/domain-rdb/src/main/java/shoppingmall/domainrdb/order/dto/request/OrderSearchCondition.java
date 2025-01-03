package shoppingmall.domainrdb.order.dto.request;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Pageable;
import shoppingmall.domainrdb.domain.order.entity.OrderStatus;


@Getter
public class OrderSearchCondition {
    private OrderStatus orderStatus;
    private Pageable pageable;


    @Builder
    @QueryProjection
    public OrderSearchCondition(OrderStatus orderStatus, Pageable pageable) {
        this.orderStatus = orderStatus;
        this.pageable = pageable;
    }
}
