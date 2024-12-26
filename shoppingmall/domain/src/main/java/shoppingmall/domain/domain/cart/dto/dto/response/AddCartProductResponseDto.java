package shoppingmall.domain.domain.cart.dto.dto.response;


import lombok.Builder;
import lombok.Getter;
import shoppingmall.domain.domain.cart.entity.CartProduct;

@Getter
public class AddCartProductResponseDto {
    private Long cartProductId;
    private int quantity;


    @Builder
    private AddCartProductResponseDto(Long cartProductId, int quantity) {
        this.cartProductId = cartProductId;
        this.quantity = quantity;
    }

    public static AddCartProductResponseDto from(CartProduct cartProduct) {
        return new AddCartProductResponseDto(cartProduct.getId(), cartProduct.getQuantity());

    }
}
