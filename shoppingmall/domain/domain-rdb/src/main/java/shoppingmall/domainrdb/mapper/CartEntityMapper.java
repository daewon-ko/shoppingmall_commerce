package shoppingmall.domainrdb.mapper;

import shoppingmall.domainrdb.cart.CartDomain;
import shoppingmall.domainrdb.cart.entity.Cart;

public class CartEntityMapper {
    public static CartDomain toCartDomain(Cart cart) {
        return CartDomain.builder()
                .userDomain(UserEntityMapper.toUserDomain(cart.getUser()))
                .build();
    }
}
