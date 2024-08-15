package shppingmall.commerce.order.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shppingmall.commerce.global.ApiResponse;
import shppingmall.commerce.order.dto.request.OrderCreateRequestDto;
import shppingmall.commerce.order.dto.response.OrderProductCreateResponseDto;
import shppingmall.commerce.order.service.OrderService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController {
    private final OrderService orderService;


    // 바로 주문
    @PostMapping()
    public ApiResponse<List<OrderProductCreateResponseDto>> createOrder(@RequestBody @Valid OrderCreateRequestDto orderCreateRequestDto) {
        return ApiResponse.ok(orderService.createDirectOrder(orderCreateRequestDto));

    }

    // 장바구니통해 주문
    @PostMapping("/cart")
    public ApiResponse<List<OrderProductCreateResponseDto>> createOrderWithCart(@RequestBody @Valid OrderCreateRequestDto orderCartCreateRequestDto) {
        return ApiResponse.ok(orderService.createOrderCart(orderCartCreateRequestDto));
    }

}
