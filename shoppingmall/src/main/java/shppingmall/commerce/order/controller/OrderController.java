package shppingmall.commerce.order.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shppingmall.commerce.order.dto.OrderCreateRequestDto;
import shppingmall.commerce.order.service.OrderService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/order")
    public ResponseEntity<String> createOrder(@RequestBody OrderCreateRequestDto orderCreateRequestDto) {
        orderService.createOrder(orderCreateRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body("OK");

    }
}
