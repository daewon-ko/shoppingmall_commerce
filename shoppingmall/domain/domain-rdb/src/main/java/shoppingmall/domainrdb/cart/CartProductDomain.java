package shoppingmall.domainrdb.cart;

import lombok.Getter;
import shoppingmall.domainrdb.product.domain.ProductId;

@Getter
public class CartProductDomain {
    private final CartProductId cartProductId;
    private final int quantity;
    private final CartId cartId;
    private final ProductId productId;


    private CartProductDomain(CartProductId cartProductId, int quantity, CartId cartId, ProductId productId) {
        this.cartProductId = cartProductId;
        this.quantity = quantity;
        this.cartId = cartId;
        this.productId = productId;
    }

    public static CartProductDomain createForWrite(ProductId productId, CartId cartId, int quantity) {
        return new CartProductDomain(null, quantity, cartId, productId);
    }

    public static CartProductDomain createForRead(Long id, ProductId productId, CartId cartId, int quantity) {
        return new CartProductDomain(CartProductId.from(id), quantity, cartId, productId);
    }
}
