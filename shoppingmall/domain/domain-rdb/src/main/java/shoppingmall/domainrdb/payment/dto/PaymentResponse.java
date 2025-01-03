package shoppingmall.domainrdb.payment.dto;

import lombok.Builder;
import lombok.Getter;
import shoppingmall.domainrdb.domain.payment.entity.TossPayment;
import shoppingmall.tosspayment.feign.TossPaymentMethod;
import shoppingmall.tosspayment.feign.TossPaymentStatus;

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
