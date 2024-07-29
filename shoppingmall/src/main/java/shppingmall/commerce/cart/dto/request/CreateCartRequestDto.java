package shppingmall.commerce.cart.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import shppingmall.commerce.cart.entity.Cart;

import java.time.LocalDateTime;

@Getter
public class CreateCartRequestDto {
    @NotNull(message = "Cart 번호는 공백일 수 없습니다.")
    private long cartId;



    public Cart toEntity() {
        return Cart.builder()
                .id(getCartId())
                .build();
    }


}
