package shoppingmall.domainrdb.payment;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TossPaymentCancelEvent {
    private final String paymentKey;
    private final String cancelReason;




}
