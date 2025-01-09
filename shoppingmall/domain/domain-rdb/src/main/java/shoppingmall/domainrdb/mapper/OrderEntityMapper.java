package shoppingmall.domainrdb.mapper;

import shoppingmall.domainrdb.order.AddressDomain;
import shoppingmall.domainrdb.order.OrderDomain;
import shoppingmall.domainrdb.order.entity.Order;
import shoppingmall.domainrdb.user.UserDomain;

public class OrderEntityMapper {
    public static Order toOrderEntity(final OrderDomain orderDomain) {
        return Order.builder()
                .user(UserEntityMapper.toUserEntity(orderDomain.getUserDomain()))
//                .cart(orderDomain.)
                .detailAddress(orderDomain.getAddressDomain().getDetailAddress())
                .zipCode(orderDomain.getAddressDomain().getZipCode())
                .orderStatus(orderDomain.getOrderStatus())
                .build();
    }

    public static OrderDomain toOrderDomain(final Order order) {
        return OrderDomain.builder()
                .userDomain(UserEntityMapper.toUserDomain(order.getUser()))
                .addressDomain(AddressDomain.builder().detailAddress(order.getDetailAddress()).zipCode(order.getZipCode()).build())
                .orderStatus(order.getOrderStatus())
                .build();
    }
}
