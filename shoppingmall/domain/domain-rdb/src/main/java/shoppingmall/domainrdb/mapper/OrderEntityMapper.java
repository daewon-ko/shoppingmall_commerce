package shoppingmall.domainrdb.mapper;

import shoppingmall.domainrdb.cart.CartDomain;
import shoppingmall.domainrdb.order.domain.AddressDomain;
import shoppingmall.domainrdb.order.domain.OrderDomain;
import shoppingmall.domainrdb.order.entity.Order;
import shoppingmall.domainrdb.user.UserDomain;
import shoppingmall.domainrdb.user.UserId;
import shoppingmall.domainrdb.user.entity.User;

public class OrderEntityMapper {
    public static Order toOrderEntityWithOutCart(final OrderDomain orderDomain) {
        User userEntity = User.fromUserId(orderDomain.getUserId());
        return Order.builder()
                .user(userEntity)
                .detailAddress(orderDomain.getAddressDomain().getDetailAddress())
                .zipCode(orderDomain.getAddressDomain().getZipCode())
                .orderStatus(orderDomain.getOrderStatus())
                .build();
    }

    public static Order toOrderEntityWithCart(final OrderDomain orderDomain, final UserDomain userDomain, final CartDomain cartDomain) {
        return Order.builder()
                .user(UserEntityMapper.toUserEntity(userDomain))
                .cart(CartEntityMapper.toCartEntity(cartDomain))
                .zipCode(orderDomain.getAddressDomain().getZipCode())
                .detailAddress(orderDomain.getAddressDomain().getDetailAddress())
                .orderStatus(orderDomain.getOrderStatus())
                .build();
    }

    public static OrderDomain toOrderDomain(final Order order) {
        return OrderDomain.createForRead(order.getId(), order.getOrderStatus(), UserId.from(order.getUser().getId()), AddressDomain.builder().zipCode(order.getZipCode()).zipCode(order.getZipCode()).build());
    }


}
