package shppingmall.commerce.cart.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import shppingmall.commerce.cart.entity.CartProduct;

@Getter
public class AddCartProductResponseDto {
    private Long cartProductId;
    private int quantity;


    @Builder
    private AddCartProductResponseDto(Long cartProductId, int quantity) {
        this.cartProductId = cartProductId;
        this.quantity = quantity;
    }

    public static AddCartProductResponseDto of(CartProduct cartProduct) {
        return new AddCartProductResponseDto(cartProduct.getId(), cartProduct.getQuantity());

    }
}
