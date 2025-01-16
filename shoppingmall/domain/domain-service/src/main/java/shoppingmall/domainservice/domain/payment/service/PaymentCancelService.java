package shoppingmall.domainservice.domain.payment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import shoppingmall.common.exception.ApiException;
import shoppingmall.common.exception.domain.PaymentErrorCode;
import shoppingmall.domainrdb.common.annotation.DomainRdbService;
import shoppingmall.domainrdb.payment.TossPaymentCancelEvent;

@DomainRdbService
@RequiredArgsConstructor
@Slf4j
public class PaymentCancelService {

    private final ApplicationEventPublisher eventPublisher;

    public void cancelPayment(final TossPaymentCancelEvent tossPaymentCancelEvent) {
        eventPublisher.publishEvent(tossPaymentCancelEvent);





    }
}
