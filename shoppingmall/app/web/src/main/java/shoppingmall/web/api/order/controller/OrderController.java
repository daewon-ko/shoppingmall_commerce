package shoppingmall.web.api.order.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import shoppingmall.common.ApiResponse;
import shoppingmall.domainrdb.order.domain.OrderStatus;
import shoppingmall.domainrdb.order.dto.OrderProductResponseDto;
import shoppingmall.domainrdb.order.dto.OrderSearchCondition;
import shoppingmall.domainservice.domain.order.dto.request.OrderCreateRequestDto;
import shoppingmall.domainservice.domain.order.dto.request.OrderUpdateRequest;
import shoppingmall.domainservice.domain.order.dto.response.OrderProductCreateResponseDto;
import shoppingmall.web.api.order.usecase.OrderUsecase;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class OrderController {
    private final OrderUsecase orderUsecase;



    // 바로 주문
    // Buyer만 주문할 수 있도록 설정
    @PreAuthorize("hasRole('BUYER')")
    @PostMapping("/order")
    public ApiResponse<List<OrderProductCreateResponseDto>> createOrder(@RequestHeader("idempotency-key") String idempotenceKey, @RequestBody  OrderCreateRequestDto orderCreateRequestDto) {
        return ApiResponse.ok(orderUsecase.createDirectOrder(idempotenceKey, orderCreateRequestDto));

    }

    // 장바구니통해 주문
    @PostMapping("/order/cart")
    @PreAuthorize("hasRole('BUYER')")
    public ApiResponse<List<OrderProductCreateResponseDto>> createOrderWithCart(@RequestBody  OrderCreateRequestDto orderCartCreateRequestDto) {
        return ApiResponse.ok(orderUsecase.createOrderWithCart(orderCartCreateRequestDto));
    }

    //     주문목록 조회(인증 부분은 일단 제외)
    //    Buyer만 주문목록 조회할 수 있도록 설정
    @GetMapping("/order/{userId}/list")
    @PreAuthorize("hasRole('BUYER')")
    public ApiResponse<Slice<OrderProductResponseDto>> getOrderDetail(
//            @AuthUserId
            @PathVariable(name = "userId") Long userId,
//            @SortDefault(sort= )
            @RequestParam OrderStatus orderStatus,
            Pageable pageable
    ) {
        OrderSearchCondition orderSearchCondition = OrderSearchCondition.builder()
                .orderStatus(orderStatus).build();

        Slice<OrderProductResponseDto> orderList = orderUsecase.getOrderList(userId, orderSearchCondition, pageable);
        return ApiResponse.ok(orderList);
    }


    //TODO : OrderStatus만 변경하므로 PatchMapping이 더 적합하지 않을까?
    // 그러나 주문 전체를 바꾼다는 관점에선 PutMapping이 더 적절해보이기도 한다.
    @PutMapping("/order/{orderId}")
    @PreAuthorize("hasRole('BUYER')")
    public ApiResponse cancelOrder(
            @PathVariable(name = "orderId") Long orderId
    ) {
        orderUsecase.cancelOrder(orderId);
        return ApiResponse.ok();
    }

    @PatchMapping("/order/{orderId}")
    @PreAuthorize("hasRole('BUYER')")
    public ApiResponse updateOrder(
            @RequestBody OrderUpdateRequest orderUpdateRequest
    ) {
        orderUsecase.updateOrder(orderUpdateRequest);
        return ApiResponse.ok();

    }


}
