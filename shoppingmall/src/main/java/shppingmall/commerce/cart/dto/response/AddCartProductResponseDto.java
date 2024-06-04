package shppingmall.commerce.cart.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import shppingmall.commerce.cart.entity.CartProduct;

@Getter
@AllArgsConstructor
public class AddCartProductResponseDto {
    private Long cartProductId;
    private int quantity;



    public static AddCartProductResponseDto of(CartProduct cartProduct) {
        return new AddCartProductResponseDto(cartProduct.getId(), cartProduct.getQuantity());

    }
}
