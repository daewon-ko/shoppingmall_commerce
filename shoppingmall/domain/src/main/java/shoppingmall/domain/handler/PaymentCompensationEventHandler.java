package shoppingmall.domain.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import shoppingmall.domain.domain.payment.TossPaymentCancelEvent;
import shoppingmall.tosspayment.feign.PaymentClient;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentCompensationEventHandler {
    private final PaymentClient paymentClient;


    @TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
    public void issueCancelPaymentEvent(TossPaymentCancelEvent tossPaymentCancelEvent) {
        paymentClient.cancelPayment(tossPaymentCancelEvent.getPaymentKey(), tossPaymentCancelEvent.getCancelReason());


    }


}
