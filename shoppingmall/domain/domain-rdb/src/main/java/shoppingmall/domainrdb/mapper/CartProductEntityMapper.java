package shoppingmall.domainrdb.mapper;

import shoppingmall.domainrdb.cart.CartId;
import shoppingmall.domainrdb.cart.CartProductDomain;
import shoppingmall.domainrdb.cart.entity.CartProduct;
import shoppingmall.domainrdb.product.domain.ProductId;

public class CartProductEntityMapper {
    public static CartProductDomain toCartProductDomain(CartProduct cartProduct) {

        return CartProductDomain.createForRead(cartProduct.getId(), ProductId.from(cartProduct.getProduct().getId()), CartId.from( cartProduct.getCart().getId()), cartProduct.getQuantity());



    }
}
