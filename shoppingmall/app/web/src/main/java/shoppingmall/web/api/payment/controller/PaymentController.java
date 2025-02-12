package shoppingmall.web.api.payment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shoppingmall.common.dto.toss.TossPaymentConfirmRequest;
import shoppingmall.domainservice.domain.payment.dto.PaymentResponse;
import shoppingmall.web.api.payment.usecase.PaymentUsecase;


@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentUsecase paymentUsecase;


    @PostMapping("/confirm/{orderId}")
    public ResponseEntity<PaymentResponse> confirmPayment(@RequestBody TossPaymentConfirmRequest tossPaymentConfirmRequest) {

        PaymentResponse paymentResponse = paymentUsecase.executePayment(tossPaymentConfirmRequest);

        return ResponseEntity.status(HttpStatus.OK).body(paymentResponse);
    }

    /**
     * TossPayment에 저장된 PK값인 paymentId로 조회
     * @param paymentId
     * @return
     */
    @GetMapping("/{paymentId}")
    public ResponseEntity<PaymentResponse> getPayment(@PathVariable("paymentId") Long paymentId) {
        PaymentResponse paymentResponse = paymentUsecase.getPayment(paymentId);
        return ResponseEntity.status(HttpStatus.OK).body(paymentResponse);
    }
}
