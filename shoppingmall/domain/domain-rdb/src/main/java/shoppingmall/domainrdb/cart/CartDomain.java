package shoppingmall.domainrdb.cart;

import lombok.Builder;
import lombok.Getter;
import shoppingmall.domainrdb.user.UserDomain;

@Getter
public class CartDomain {
    private final UserDomain userDomain;

    @Builder
    private CartDomain(UserDomain userDomain) {
        this.userDomain = userDomain;
    }

}
