package shppingmall.commerce.cart.dto.request;

import lombok.Getter;
import shppingmall.commerce.cart.entity.Cart;
import shppingmall.commerce.cart.entity.CartProduct;
import shppingmall.commerce.product.entity.Product;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class AddCartRequestDto {
    private Long cartId;
    private List<AddCartProductRequestDto> cartProductRequestDtoList;

    public CartProduct toEntity(Cart cart, Product product, int quantity) {
        return CartProduct.builder()
                .cart(cart)
                .product(product)
                .quantity(quantity)
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .build();
    }
}
