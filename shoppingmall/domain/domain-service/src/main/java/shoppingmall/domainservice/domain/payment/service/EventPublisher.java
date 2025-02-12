package shoppingmall.domainservice.domain.payment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import shoppingmall.domainservice.common.annotation.DomainService;

@DomainService
@RequiredArgsConstructor
@Slf4j
public class EventPublisher<T> {

    private final ApplicationEventPublisher eventPublisher;

    public  void publishEvent(final T t) {
        eventPublisher.publishEvent(t);

    }
}
