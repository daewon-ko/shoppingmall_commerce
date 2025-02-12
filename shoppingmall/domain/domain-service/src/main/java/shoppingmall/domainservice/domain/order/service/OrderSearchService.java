package shoppingmall.domainservice.domain.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import shoppingmall.domainrdb.common.annotation.DomainRdbService;
import shoppingmall.domainrdb.order.domain.OrderStatus;
import shoppingmall.domainrdb.order.dto.OrderProductResponseDto;
import shoppingmall.domainrdb.order.dto.OrderSearchCondition;
import shoppingmall.domainrdb.order.service.OrderRdbService;

@DomainRdbService
@RequiredArgsConstructor
public class OrderSearchService {
    private final OrderRdbService orderRdbService;

    public Slice<OrderProductResponseDto> searchOrders(final Long userId, final OrderSearchCondition orderSearchCondition, final Pageable pageable) {
        OrderStatus orderStatus = orderSearchCondition.getOrderStatus();

        // userId로 User를 찾아서 없으면 예외처리

        // UserDomain으로 Order 조회


        return orderRdbService.getOrderList(userId, orderSearchCondition, pageable);

        // Slice<OrderProductDomain>을 Slice<OrderProductResponseDto>로 변환
//        orderList.stream().map(orderProductDomain -> OrderProductResponseDto.builder()
//                .orderId(orderProductDomain.getOrderId().getValue())
//                .productId(orderProductDomain.getOrderProductId().getValue())
//                .productName(orderProductDomain.)
//                .price(orderProductDomain.getPrice())
//                .quantity(orderProductDomain.getQuantity())
//                .build());


    }
}
