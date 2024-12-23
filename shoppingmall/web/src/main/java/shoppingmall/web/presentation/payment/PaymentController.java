package shoppingmall.web.presentation.payment;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shoppingmall.core.domain.payment.dto.PaymentResponse;
import shoppingmall.core.domain.payment.service.PaymentService;
import shoppingmall.tosspayment.feign.dto.TossPaymentConfirmRequest;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;


    @PostMapping("/confirm/{orderId}")
    public ResponseEntity<PaymentResponse> confirmPayment(@RequestBody TossPaymentConfirmRequest tossPaymentConfirmRequest, @PathVariable("orderId") Long orderId) {

        PaymentResponse paymentResponse = paymentService.confirm(tossPaymentConfirmRequest, orderId);
        return ResponseEntity.status(HttpStatus.OK).body(paymentResponse);
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<PaymentResponse> getPayment(@PathVariable("paymentId") Long paymentId) {
        PaymentResponse paymentResponse = paymentService.getPayment(paymentId);
        return ResponseEntity.status(HttpStatus.OK).body(paymentResponse);
    }
}
