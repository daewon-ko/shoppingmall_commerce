package shoppingmall.domainrdb.cart;

import lombok.Builder;
import lombok.Getter;
import shoppingmall.domainrdb.product.ProductDomain;

@Getter
public class CartProductDomain {
    private Long cartProductId;
    private final int quantity;
    private final CartDomain cartDomain;
    private final ProductDomain productDomain;


    @Builder

    public CartProductDomain(int quantity, CartDomain cartDomain, ProductDomain productDomain, Long cartProductId) {
        this.quantity = quantity;
        this.cartDomain = cartDomain;
        this.productDomain = productDomain;
        this.cartProductId = cartProductId;
    }
}
