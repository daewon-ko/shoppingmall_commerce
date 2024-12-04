package shppingmall.commerce.payment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import shppingmall.commerce.payment.entity.TossPaymentMethod;
import shppingmall.commerce.payment.entity.TossPaymentStatus;

@Getter
@ToString
public class PaymentConfirmResponse {

    private String orderId;

    private String paymentKey;

    private Long totalAmount;

    private String status;

    private String method;

    private String requestedAt;

    private String approvedAt;

    @Builder
    private PaymentConfirmResponse(String orderId, String paymentKey, Long amount, String status, String method, String  requestedAt, String approvedAt) {
        this.orderId = orderId;
        this.paymentKey = paymentKey;
        this.totalAmount = amount;
        this.status = status;
        this.method = method;
        this.requestedAt = requestedAt;
        this.approvedAt = approvedAt;
    }




}
