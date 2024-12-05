package shppingmall.commerce.payment.dto;

import lombok.Builder;
import lombok.Getter;
import shppingmall.commerce.payment.entity.TossPayment;
import shppingmall.commerce.payment.entity.TossPaymentMethod;
import shppingmall.commerce.payment.entity.TossPaymentStatus;

@Getter
public class PaymentResponse {
    private Long paymentId;
    private Long orderId;
    private TossPaymentMethod tossPaymentMethod;
    private TossPaymentStatus tossPaymentStatus;
    private Long amount;


    @Builder
    private PaymentResponse(Long paymentId, Long orderId, TossPaymentMethod tossPaymentMethod, TossPaymentStatus tossPaymentStatus, Long amount) {
        this.paymentId = paymentId;
        this.orderId = orderId;
        this.tossPaymentMethod = tossPaymentMethod;
        this.tossPaymentStatus = tossPaymentStatus;
        this.amount = amount;
    }

    public static PaymentResponse from(TossPayment tossPayment) {
        return PaymentResponse.builder()
                .paymentId(tossPayment.getPaymentId())
                .orderId(tossPayment.getOrder().getId())
                .tossPaymentStatus(tossPayment.getTossPaymentStatus())
                .tossPaymentMethod(tossPayment.getTossPaymentMethod())
                .amount(tossPayment.getAmount())
                .build();
    }
}
