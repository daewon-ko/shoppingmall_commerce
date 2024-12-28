package shoppingmall.common.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class TossPaymentConfirmResponse {

    private String orderId;

    private String paymentKey;

    private Long totalAmount;

    private String status;

    private String method;

    private String requestedAt;

    private String approvedAt;

    @Builder
    private TossPaymentConfirmResponse(String orderId, String paymentKey, Long amount, String status, String method, String  requestedAt, String approvedAt) {
        this.orderId = orderId;
        this.paymentKey = paymentKey;
        this.totalAmount = amount;
        this.status = status;
        this.method = method;
        this.requestedAt = requestedAt;
        this.approvedAt = approvedAt;
    }




}
