package shoppingmall.domainrdb.cart;

import lombok.Builder;
import lombok.Getter;
import shoppingmall.domainrdb.user.UserDomain;

@Getter
public class CartDomain {
    private final CartId cartId;
    private final UserDomain userDomain;

    public CartDomain(CartId cartId, UserDomain userDomain) {
        this.cartId = cartId;
        this.userDomain = userDomain;
    }
}
