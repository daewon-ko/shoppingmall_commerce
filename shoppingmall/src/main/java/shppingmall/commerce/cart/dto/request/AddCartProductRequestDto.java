package shppingmall.commerce.cart.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AddCartProductRequestDto {
    @NotNull(message = "상품번호가 선택되지 않았습니다.")
    private Long productId;
    @PositiveOrZero(message = "수량은 음수일 수 없습니다.")
    @Max(value = 9999L, message = "수량은 최대 9999개까지 입력가능합니다.")
    private int quantity;


    @Builder
    private AddCartProductRequestDto(Long productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }
}
