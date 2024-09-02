package shppingmall.commerce.order.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.*;
import shppingmall.commerce.global.ApiResponse;
import shppingmall.commerce.order.OrderStatus;
import shppingmall.commerce.order.dto.request.OrderCreateRequestDto;
import shppingmall.commerce.order.dto.request.OrderSearchCondition;
import shppingmall.commerce.order.dto.response.OrderProductCreateResponseDto;
import shppingmall.commerce.order.dto.response.OrderProductResponseDto;
import shppingmall.commerce.order.entity.Order;
import shppingmall.commerce.order.repository.OrderRepository;
import shppingmall.commerce.order.service.OrderService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
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
            @PathVariable(name ="userId" ) Long userId,
//            @SortDefault(sort= )
            @RequestParam OrderStatus orderStatus,
            Pageable pageable
    ) {
        OrderSearchCondition orderSearchCondition = OrderSearchCondition.builder()
                .orderStatus(orderStatus).build();

        Slice<OrderProductResponseDto> orderList = orderService.getOrderList(userId, orderSearchCondition, pageable);
        return ApiResponse.ok(orderList);
    }
}
