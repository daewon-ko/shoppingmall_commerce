package shoppingmall.domainservice.domain.order.dto.request;

import lombok.Builder;
import lombok.Getter;
import shoppingmall.domainservice.common.type.request.RequestType;

import java.util.List;

@Getter
public class OrderUpdateRequest {
    private RequestType requestType;
    private Long orderId;
    private List<OrderProductUpdateRequest> updateRequestList;


    @Builder
    private OrderUpdateRequest(RequestType requestType, Long orderId, List<OrderProductUpdateRequest> updateRequestList) {
        this.requestType = requestType;
        this.orderId = orderId;
        this.updateRequestList = updateRequestList;
    }

}
