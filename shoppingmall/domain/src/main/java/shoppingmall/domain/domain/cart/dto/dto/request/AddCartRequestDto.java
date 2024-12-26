package shoppingmall.domain.domain.cart.dto.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import shoppingmall.domain.domain.product.entity.Product;
import shoppingmall.domain.domain.cart.entity.Cart;
import shoppingmall.domain.domain.cart.entity.CartProduct;

import java.util.List;

@Getter
public class AddCartRequestDto {
    @NotNull(message = "카트 번호가 선택되지 않았습니다.")
    private Long cartId;
    // TODO : DTO가 객체를 품고있을 경우 Validation은 어떻게 해주는게 적절할까? -> 해당 객체에서 Validation을 해준다.
    private List<AddCartProductRequestDto> cartProductRequestDtoList;


    @Builder
    private AddCartRequestDto(Long cartId, List<AddCartProductRequestDto> cartProductRequestDtoList) {
        this.cartId = cartId;
        this.cartProductRequestDtoList = cartProductRequestDtoList;
    }

    public CartProduct toEntity(Cart cart, Product product, int quantity) {
        return CartProduct.builder()
                .cart(cart)
                .product(product)
                .quantity(quantity)
                .build();
    }
}
