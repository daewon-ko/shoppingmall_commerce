package shoppingmall.common.dto.toss;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@ToString
public class TossPaymentConfirmResponse implements Serializable {

    // Toss서버에서 내려받는 결제 정보(WAS 내부와는 무관)
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
