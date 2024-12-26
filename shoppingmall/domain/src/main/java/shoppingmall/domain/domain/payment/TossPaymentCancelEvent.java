package shoppingmall.domain.domain.payment;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.function.Consumer;

@Getter
@RequiredArgsConstructor
public class TossPaymentCancelEvent {
    private final String paymentKey;
    private final String cancelReason;




}
