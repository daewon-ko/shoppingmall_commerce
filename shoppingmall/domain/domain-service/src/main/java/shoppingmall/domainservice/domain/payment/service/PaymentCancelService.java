package shoppingmall.domainservice.domain.payment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import shoppingmall.domainrdb.payment.TossPaymentCancelEvent;
import shoppingmall.domainservice.common.annotation.DomainService;

@DomainService
@RequiredArgsConstructor
@Slf4j
public class PaymentCancelService {

    private final ApplicationEventPublisher eventPublisher;

    public void cancelPayment(final TossPaymentCancelEvent tossPaymentCancelEvent) {
        eventPublisher.publishEvent(tossPaymentCancelEvent);





    }
}
