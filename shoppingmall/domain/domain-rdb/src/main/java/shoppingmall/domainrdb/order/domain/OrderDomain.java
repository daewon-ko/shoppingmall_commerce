package shoppingmall.domainrdb.order.domain;

import lombok.Getter;
import shoppingmall.domainrdb.user.UserId;

import java.util.List;

@Getter
public class OrderDomain {
    private final OrderId orderId;
    private final OrderStatus orderStatus;
//    private final List<OrderProductId> orderProductId;
    private final UserId userId;
    private AddressDomain addressDomain;


    private OrderDomain(OrderId orderId, OrderStatus orderStatus, UserId userId, AddressDomain addressDomain) {
        this.orderId = orderId;
        this.orderStatus = orderStatus;
        this.userId = userId;
        this.addressDomain = addressDomain;
    }


    public static OrderDomain createForWrite(OrderStatus orderStatus, UserId userId, AddressDomain addressDomain) {
        return new OrderDomain(null, orderStatus, userId, addressDomain);
    }

    public static OrderDomain createForRead(Long id, OrderStatus orderStatus, UserId userId, AddressDomain addressDomain) {
        return new OrderDomain(OrderId.from(id), orderStatus, userId, addressDomain);
    }



}
