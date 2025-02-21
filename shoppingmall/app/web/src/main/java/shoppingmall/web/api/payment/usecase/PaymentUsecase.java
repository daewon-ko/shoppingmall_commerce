package shoppingmall.web.api.payment.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import shoppingmall.common.dto.toss.TossPaymentConfirmRequest;
import shoppingmall.domainredis.common.annotation.DistributedLock;
import shoppingmall.domainservice.domain.payment.dto.PaymentResponse;
import shoppingmall.domainservice.domain.payment.service.PaymentConfirmService;
import shoppingmall.domainservice.domain.payment.service.PaymentSearchService;
import shoppingmall.web.common.annotataion.Usecase;
import shoppingmall.web.common.validation.payment.TossPaymentConfirmValidator;

@Usecase
@RequiredArgsConstructor
public class PaymentUsecase {
    private final TossPaymentConfirmValidator tossPaymentConfirmValidator;
    private final PaymentConfirmService paymentConfirmService;
    private final PaymentSearchService paymentSearchService;



    @Transactional
    @DistributedLock(key = "#idempotenceKey")
    public PaymentResponse executePayment(final String idempotenceKey ,final TossPaymentConfirmRequest tossPaymentConfirmRequest) {
        // TossPaymentConfirmRequest Validation
        tossPaymentConfirmValidator.validate(tossPaymentConfirmRequest);


        return paymentConfirmService.confirm(tossPaymentConfirmRequest);
    }


    public PaymentResponse getPayment(Long paymentId) {
        return paymentSearchService.getPayment(paymentId);

    }
}
