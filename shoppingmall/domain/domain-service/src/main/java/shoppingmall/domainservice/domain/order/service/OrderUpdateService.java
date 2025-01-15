package shoppingmall.domainservice.domain.order.service;

import lombok.RequiredArgsConstructor;
import shoppingmall.common.exception.ApiException;
import shoppingmall.common.exception.domain.OrderErrorCode;
import shoppingmall.domainrdb.common.annotation.DomainRdbService;
import shoppingmall.domainrdb.order.domain.OrderDomain;
import shoppingmall.domainrdb.order.domain.OrderStatus;
import shoppingmall.domainrdb.order.service.OrderProductRdbService;
import shoppingmall.domainrdb.order.service.OrderRdbService;
import shoppingmall.domainservice.domain.order.dto.request.OrderProductUpdateRequest;
import shoppingmall.domainservice.domain.order.dto.request.OrderUpdateRequest;

import java.util.List;

@DomainRdbService
@RequiredArgsConstructor
public class OrderUpdateService {
    private final OrderRdbService orderRdbService;
    private final OrderProductRdbService orderProductRdbService;

    public void updateOrderProduct(final OrderUpdateRequest orderUpdateRequest) {
        // OrderProductDomain을 조회
        // OrderProductDomain을 업데이트
        // OrderProductDomain을 저장
        Long orderId = orderUpdateRequest.getOrderId();

        List<OrderProductUpdateRequest> updateRequestList = orderUpdateRequest.getUpdateRequestList();

        // OrderDomain 조회 후 주문상태가 배송완료 또는 취소인 경우 주문 업데이트가 불가능
        OrderDomain orderDomain = orderRdbService.findOrderDomainById(orderId);
        if (orderDomain.getOrderStatus() == OrderStatus.DELIVERY_FINISHED || orderDomain.getOrderStatus() == OrderStatus.CANCELED) {
            throw new ApiException(OrderErrorCode.ORDER_UPDATE_NOT_ALLOWED);
        }

        // 주문상품 수량 등 변경
        updateRequestList.forEach(orderProductUpdateRequest -> {
            // 특정 주문번호 및 특정 상품번호와 연관된 주문상품을 조회한다.
            orderProductRdbService.updateOrderProducts(orderId, orderProductUpdateRequest.getProductId(), orderProductUpdateRequest.getQuantity());
        });

    }
}
