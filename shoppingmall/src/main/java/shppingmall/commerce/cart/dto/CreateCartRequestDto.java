package shppingmall.commerce.cart.dto;

import lombok.Getter;
import shppingmall.commerce.cart.entity.Cart;

import java.time.LocalDateTime;

@Getter
public class CreateCartRequestDto {
    private long CartId;



    public Cart toEntity() {
        return Cart.builder()
                .id(getCartId())
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .build();
    }


}
