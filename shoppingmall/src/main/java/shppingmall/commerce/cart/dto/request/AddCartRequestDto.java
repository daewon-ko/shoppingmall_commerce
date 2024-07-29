package shppingmall.commerce.cart.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import shppingmall.commerce.cart.entity.Cart;
import shppingmall.commerce.cart.entity.CartProduct;
import shppingmall.commerce.product.entity.Product;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class AddCartRequestDto {
    @NotNull(message = "카트 번호가 선택되지 않았습니다.")
    private Long cartId;
    // TODO : DTO가 객체를 품고있을 경우 Validation은 어떻게 해주는게 적절할까? -> 해당 객체에서 Validation을 해준다.
    private List<AddCartProductRequestDto> cartProductRequestDtoList;

    public CartProduct toEntity(Cart cart, Product product, int quantity) {
        return CartProduct.builder()
                .cart(cart)
                .product(product)
                .quantity(quantity)
                .build();
    }
}
