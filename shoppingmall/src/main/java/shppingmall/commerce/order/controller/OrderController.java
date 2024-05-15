package shppingmall.commerce.order.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shppingmall.commerce.order.dto.OrderCartCreateRequestDto;
import shppingmall.commerce.order.dto.OrderCreateRequestDto;
import shppingmall.commerce.order.service.OrderService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController {
    private final OrderService orderService;


    // 바로 주문
    @PostMapping()
    public ResponseEntity<String> createOrder(@RequestBody OrderCreateRequestDto orderCreateRequestDto) {
        orderService.createOrder(orderCreateRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body("OK");

    }

    // 장바구니통해 주문
    @PostMapping("/cart")
    public ResponseEntity<String> createOrderWithCart(@RequestBody OrderCartCreateRequestDto orderCartCreateRequestDto) {
        orderService.createOrderCart(orderCartCreateRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("OK");
    }

}
