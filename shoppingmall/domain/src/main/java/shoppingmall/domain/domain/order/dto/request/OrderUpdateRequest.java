package shoppingmall.domain.domain.order.dto.request;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class OrderUpdateRequest {
    private Long orderId;
    private List<OrderProductUpdateRequest> updateRequestList;

    @Builder
    private OrderUpdateRequest(Long orderId, List<OrderProductUpdateRequest> updateRequestList) {
        this.orderId = orderId;
        this.updateRequestList = updateRequestList;
    }
}
