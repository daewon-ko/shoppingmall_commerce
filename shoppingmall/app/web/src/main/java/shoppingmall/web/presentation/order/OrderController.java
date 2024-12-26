package shoppingmall.web.presentation.order;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.*;
import shoppingmall.common.ApiResponse;
import shoppingmall.domain.domain.order.dto.request.OrderCreateRequestDto;
import shoppingmall.domain.domain.order.dto.request.OrderSearchCondition;
import shoppingmall.domain.domain.order.dto.request.OrderUpdateRequest;
import shoppingmall.domain.domain.order.dto.response.OrderProductCreateResponseDto;
import shoppingmall.domain.domain.order.dto.response.OrderProductResponseDto;
import shoppingmall.domain.domain.order.entity.OrderStatus;
import shoppingmall.domain.domain.order.service.OrderService;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class OrderController {
    private final OrderService orderService;


    // 바로 주문
    @PostMapping("/order")
    public ApiResponse<List<OrderProductCreateResponseDto>> createOrder(@RequestBody @Valid OrderCreateRequestDto orderCreateRequestDto) {
        return ApiResponse.ok(orderService.createDirectOrder(orderCreateRequestDto));

    }

    // 장바구니통해 주문
    @PostMapping("/order/cart")
    public ApiResponse<List<OrderProductCreateResponseDto>> createOrderWithCart(@RequestBody @Valid OrderCreateRequestDto orderCartCreateRequestDto) {
        return ApiResponse.ok(orderService.createOrderCart(orderCartCreateRequestDto));
    }

    //     주문목록 조회(인증 부분은 일단 제외)
    @GetMapping("/order/{userId}/list")
    public ApiResponse<Slice<OrderProductResponseDto>> getOrderDetail(
//            @AuthUserId
            @PathVariable(name = "userId") Long userId,
//            @SortDefault(sort= )
            @RequestParam OrderStatus orderStatus,
            Pageable pageable
    ) {
        OrderSearchCondition orderSearchCondition = OrderSearchCondition.builder()
                .orderStatus(orderStatus).build();

        Slice<OrderProductResponseDto> orderList = orderService.getOrderList(userId, orderSearchCondition, pageable);
        return ApiResponse.ok(orderList);
    }


    //TODO : OrderStatus만 변경하므로 PatchMapping이 더 적합하지 않을까?
    // 그러나 주문 전체를 바꾼다는 관점에선 PutMapping이 더 적절해보이기도 한다.
    @PutMapping("/order/{orderId}")
    public ApiResponse cancelOrder(
            @PathVariable(name = "orderId") Long orderId
    ) {
        orderService.cancelOrder(orderId);
        return ApiResponse.ok();
    }

    @PatchMapping("/order/{orderId}")
    public ApiResponse updateOrder(
            @PathVariable(name = "orderId") Long orderId,
            @RequestBody OrderUpdateRequest orderUpdateRequest
    ) {
        orderService.updateOrderProducts(orderId, orderUpdateRequest);
        return ApiResponse.ok();

    }


}
