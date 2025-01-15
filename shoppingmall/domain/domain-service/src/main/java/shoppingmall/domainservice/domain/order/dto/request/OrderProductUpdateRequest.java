package shoppingmall.domainservice.domain.order.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderProductUpdateRequest {
    private Long productId;
    private Integer quantity;

    @Builder
    private OrderProductUpdateRequest(Long productId, Integer quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }
}
