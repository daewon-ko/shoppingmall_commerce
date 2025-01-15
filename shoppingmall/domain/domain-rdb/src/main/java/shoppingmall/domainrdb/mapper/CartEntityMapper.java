package shoppingmall.domainrdb.mapper;

import shoppingmall.domainrdb.cart.CartDomain;
import shoppingmall.domainrdb.cart.entity.Cart;

public class CartEntityMapper {


    public static Cart toCartEntity(CartDomain cartDomain) {
        return Cart.builder()
                .user(UserEntityMapper.toUserEntity(cartDomain.getUserDomain()))
                .build();
    }
}
