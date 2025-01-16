package shoppingmall.domainservice.domain.payment.service;


import lombok.RequiredArgsConstructor;
import shoppingmall.domainrdb.common.annotation.DomainRdbService;
import shoppingmall.domainrdb.payment.TossPaymentDomain;
import shoppingmall.domainrdb.payment.service.PaymentRdbService;
import shoppingmall.domainredis.domain.dto.PaymentCacheDto;
import shoppingmall.domainredis.domain.payment.service.PaymentCacheService;
import shoppingmall.domainservice.domain.payment.mapper.PaymentConverter;
import shoppingmall.domainservice.domain.payment.dto.PaymentResponse;


@DomainRdbService
@RequiredArgsConstructor
public class PaymentSearchService {
    private final PaymentRdbService paymentRdbService;
    private final PaymentCacheService paymentCacheService;

    private final PaymentConverter paymentConverter;


    public PaymentResponse getPayment(Long paymentId) {


        // Cache에서 우선적으로 조회 -> Read-Through 전략

        PaymentCacheDto paymentCache = paymentCacheService.getPaymentCache(paymentId);


        if (paymentCache != null) {
            TossPaymentDomain tossPaymentDomain = paymentConverter.from(paymentCache);
            return PaymentResponse.from(tossPaymentDomain);
        }


        // Cache Store에 없을 경우 RDB에서 조회

        TossPaymentDomain tossPaymentDomain = paymentRdbService.getPayment(paymentId);
        // TossPaymentDomain을 PaymentResponse로 변환
        PaymentResponse paymentResponse = PaymentResponse.from(tossPaymentDomain);

        // Cache Store에 저장
        PaymentCacheDto paymentCacheDto = paymentConverter.from(tossPaymentDomain);
        paymentCacheService.savePaymentCache(tossPaymentDomain.getTossPaymentId().getValue(), paymentCacheDto);

        return paymentResponse;

    }


}
