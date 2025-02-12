package shoppingmall.web.common.validation.payment;

import org.springframework.stereotype.Component;
import shoppingmall.common.dto.toss.TossPaymentConfirmRequest;

@Component
public class TossPaymentConfirmValidator {
    public void validate(final TossPaymentConfirmRequest tossPaymentConfirmRequest) {
        if (tossPaymentConfirmRequest.getOrderId() == null || tossPaymentConfirmRequest.getOrderId().isEmpty()) {
            throw new IllegalArgumentException("orderId is required");
        }
        if (tossPaymentConfirmRequest.getAmount() == null || tossPaymentConfirmRequest.getAmount().isEmpty()) {
            throw new IllegalArgumentException("amount is required");
        }
        if (tossPaymentConfirmRequest.getPaymentKey() == null || tossPaymentConfirmRequest.getPaymentKey().isEmpty()) {
            throw new IllegalArgumentException("paymentKey is required");
        }
    }
}
