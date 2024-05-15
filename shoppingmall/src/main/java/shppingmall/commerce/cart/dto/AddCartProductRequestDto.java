package shppingmall.commerce.cart.dto;

import lombok.Getter;

@Getter
public class AddCartProductRequestDto {
    private Long productId;
    private int quantity;
}
