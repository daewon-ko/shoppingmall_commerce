package shppingmall.commerce.cart.dto.request;

import lombok.Getter;

@Getter
public class AddCartProductRequestDto {
    private Long productId;
    private int quantity;
}
