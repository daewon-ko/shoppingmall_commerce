package shoppingmall.core.domain.order.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderProductUpdateRequest {
    @NotNull
    private Long productId;
    @PositiveOrZero(message = "수량은 음수일 수 없습니다.")
    @NotNull
    private Integer quantity;

    @Builder
    private OrderProductUpdateRequest(Long productId, Integer quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }
}
