package shoppingmall.domainrdb.order.domain;

import lombok.Getter;
import shoppingmall.domainrdb.order.entity.Order;
import shoppingmall.domainrdb.user.UserId;

import java.util.List;

@Getter
public class OrderDomain {
    /**
     * Long으로 선언하지 않는이유?
     * Kotlin -> Null체크 확실
     *
     * Nullable해줄 가장 좋은 방법은 **1급객체**
     * 객체에는 null을 넣어도 되니까.
     * Long은 Null 허용이 불가.
     *
     *
     *
     */
//    private final Id orderId;   // Id만 갖고있을 거므로.
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

//    public static Order toEntity() {
//
//    }


}
