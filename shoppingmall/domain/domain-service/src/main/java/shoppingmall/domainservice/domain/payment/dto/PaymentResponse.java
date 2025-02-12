package shoppingmall.domainservice.domain.payment.dto;

import lombok.Builder;
import lombok.Getter;
import shoppingmall.domainrdb.payment.TossPaymentDomain;
import shoppingmall.domainrdb.payment.entity.TossPayment;
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

    public static PaymentResponse from(TossPaymentDomain tossPaymentDomain) {
        return PaymentResponse.builder()
                .paymentId(tossPaymentDomain.getTossPaymentId().getValue())
                .orderId(tossPaymentDomain.getOrderId())
                .tossPaymentMethod(tossPaymentDomain.getTossPaymentMethod())
                .tossPaymentStatus(tossPaymentDomain.getTossPaymentStatus())
                .amount(tossPaymentDomain.getAmount())
                .build();
    }
}
