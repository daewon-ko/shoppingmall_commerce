package shppingmall.commerce.payment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import shppingmall.commerce.payment.dto.PaymentConfirmRequest;
import shppingmall.commerce.payment.dto.PaymentConfirmResponse;
import shppingmall.commerce.payment.service.PaymentService;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;


    @PostMapping("/confirm/{orderId}")
    public ResponseEntity confirmPayment(@RequestBody PaymentConfirmRequest paymentConfirmRequest, @PathVariable("orderId") Long orderId) {

        PaymentConfirmResponse paymentConfirmResponse = paymentService.confirm(paymentConfirmRequest, orderId);
        return ResponseEntity.ok(paymentConfirmResponse);
    }


}
