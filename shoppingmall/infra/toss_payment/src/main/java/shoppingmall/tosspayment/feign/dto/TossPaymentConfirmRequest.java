package shoppingmall.tosspayment.feign.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TossPaymentConfirmRequest {
    private String orderId;
    private String amount;
    private  String paymentKey;

    @Builder
    private TossPaymentConfirmRequest(String orderId, String amount, String paymentKey) {
        this.orderId = orderId;
        this.amount = amount;
        this.paymentKey = paymentKey;
    }
}
