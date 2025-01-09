package shoppingmall.domainrdb.mapper;

import shoppingmall.domainrdb.cart.CartProductDomain;
import shoppingmall.domainrdb.cart.entity.CartProduct;

public class CartProductEntityMapper {
    public static CartProductDomain toCartProductDomain(CartProduct cartProduct) {
        return CartProductDomain.builder()
                .cartProductId(cartProduct.getId())
                .productDomain(ProductEntityMapper.toProductDomain(cartProduct.getProduct()))
                .cartDomain(CartEntityMapper.toCartDomain(cartProduct.getCart()))
                .quantity(cartProduct.getQuantity())
                .build();
    }
}
