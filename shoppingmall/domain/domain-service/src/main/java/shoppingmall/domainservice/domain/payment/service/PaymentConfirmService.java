package shoppingmall.domainservice.domain.payment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import shoppingmall.common.dto.toss.TossPaymentConfirmRequest;
import shoppingmall.common.dto.toss.TossPaymentConfirmResponse;
import shoppingmall.common.exception.ApiException;
import shoppingmall.common.exception.domain.PaymentErrorCode;
import shoppingmall.domainrdb.common.annotation.DomainRdbService;
import shoppingmall.domainrdb.order.domain.OrderDomain;
import shoppingmall.domainrdb.order.service.OrderRdbService;
import shoppingmall.domainrdb.payment.TossPaymentCancelEvent;
import shoppingmall.domainrdb.payment.TossPaymentDomain;
import shoppingmall.domainrdb.payment.service.PaymentRdbService;
import shoppingmall.domainredis.domain.dto.PaymentCacheDto;
import shoppingmall.domainredis.domain.payment.service.PaymentCacheService;
import shoppingmall.domainservice.domain.payment.PaymentConverter;
import shoppingmall.domainservice.domain.payment.dto.PaymentResponse;
import shoppingmall.tosspayment.feign.PaymentClient;


@DomainRdbService
@RequiredArgsConstructor
@Slf4j
public class PaymentConfirmService {
    private final PaymentRdbService paymentRdbService;
    private final PaymentClient paymentClient;
    private final PaymentCancelService paymentCancelService;
    private final PaymentCacheService paymentCacheService;
    private final OrderRdbService orderRdbService;
    private final PaymentConverter paymentConverter;


    /**
     * 외부 API 호출 후 DB 저장시에 예외 발생시 보상 API 호출 가능하게끔 보상 트랜잭션 적용
     * <p>
     * TossPaymentCancelEvent정의하여 paymentKey, cancelReason을 넘겨준다.
     *
     * @param tossPaymentConfirmRequest
     * @return
     */

    public PaymentResponse confirm(final TossPaymentConfirmRequest tossPaymentConfirmRequest) {


        // TossPayment Confirm API 호출
        TossPaymentConfirmResponse tossPaymentConfirmResponse = paymentClient.confirmPayment(tossPaymentConfirmRequest);
        OrderDomain orderDomain = orderRdbService.findOrderDomainById(Long.parseLong(tossPaymentConfirmRequest.getOrderId()));
        TossPaymentDomain tosspaymentDomain = TossPaymentDomain.createForWrite(tossPaymentConfirmResponse, orderDomain);


        try {
            // PaymentRdbService에서 savePayment 메서드 호출
            TossPaymentDomain tossPaymentDomain = paymentRdbService.savePayment(tosspaymentDomain);

            // TossPaymentDomain을 PaymentCacheDto로 변환
            PaymentCacheDto paymentCacheDto = paymentConverter.from(tossPaymentDomain);


            // PaymentCacheService에서 savePaymentCache 메서드 호출
            paymentCacheService.savePaymentCache(tossPaymentDomain.getTossPaymentId().getValue(), paymentCacheDto);


            PaymentResponse paymentResponse = PaymentResponse.builder()
                    .paymentId(tossPaymentDomain.getTossPaymentId().getValue())
                    .orderId(orderDomain.getOrderId().getValue())
                    .tossPaymentStatus(tosspaymentDomain.getTossPaymentStatus())
                    .tossPaymentMethod(tosspaymentDomain.getTossPaymentMethod())
                    .amount(tosspaymentDomain.getAmount())
                    .build();

            return paymentResponse;


        } catch (Exception e) {
            TossPaymentCancelEvent cancelEvent = new TossPaymentCancelEvent(tossPaymentConfirmRequest.getPaymentKey(), "서버 내부 DB 저장 중 오류 발생");
            paymentCancelService.cancelPayment(cancelEvent);
            log.debug("결제 취소 수행", e);
            throw new ApiException(PaymentErrorCode.FAIL_PAYMENT);

        }


    }
}
