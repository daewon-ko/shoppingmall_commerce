package shoppingmall.domainrdb.domain.payment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shoppingmall.common.dto.TossPaymentConfirmRequest;
import shoppingmall.common.dto.TossPaymentConfirmResponse;
import shoppingmall.domainrdb.domain.order.entity.Order;
import shoppingmall.domainrdb.domain.order.repository.OrderRepository;
import shoppingmall.domainrdb.domain.payment.TossPaymentCancelEvent;
import shoppingmall.domainrdb.domain.payment.repository.PaymentRepository;
import shoppingmall.domainrdb.domain.payment.dto.PaymentResponse;
import shoppingmall.domainrdb.domain.payment.entity.TossPayment;
import shoppingmall.common.exception.ApiException;
import shoppingmall.common.exception.domain.OrderErrorCode;
import shoppingmall.common.exception.domain.PaymentErrorCode;
import shoppingmall.tosspayment.feign.PaymentClient;

import java.time.Duration;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class PaymentService {

    private final PaymentClient paymentClient;
    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    private final ApplicationEventPublisher eventPublisher;


    private static final String REDIS_PAYMENT_PREFIX = "payment_order";

    //TODO : 결제에서의 트랜잭션 처리는 어떻게?
    // 외부 API를 연동하는데 트랜잭션 처리를 어떻게 해주는게 적절할까?

    /**
     * 외부 API 호출 후 DB 저장시에 예외 발생시 보상 API 호출 가능하게끔 보상 트랜잭션 적용
     *
     * TossPaymentCancelEvent정의하여 paymentKey, cancelReason을 넘겨준다.
     *
     *
     * @param request
     * @param orderId
     * @return
     */
    public PaymentResponse confirm(final TossPaymentConfirmRequest request, final Long orderId) {
        Order findOrder = orderRepository.findById(orderId).orElseThrow(() -> new ApiException(OrderErrorCode.NOT_EXIST_ORDER));

        // 외부 API 호출
        final TossPaymentConfirmResponse tossPaymentConfirmResponse = paymentClient.confirmPayment(request);


        try {
            return executeTransactionalPayment(tossPaymentConfirmResponse, findOrder);
        } catch (Exception e) {
            TossPaymentCancelEvent cancelEvent = new TossPaymentCancelEvent(request.getPaymentKey(), "DB 오류");
            eventPublisher.publishEvent(cancelEvent);
            log.debug("결제 취소 수행", e);
            throw new ApiException(PaymentErrorCode.FAIL_PAYMENT);

        }

    }

    @Transactional
    protected PaymentResponse executeTransactionalPayment(TossPaymentConfirmResponse tossPaymentConfirmResponse, Order findOrder) {
        // TossPayment 객체 생성 및 저장
        TossPayment tossPayment = TossPayment.of(tossPaymentConfirmResponse, findOrder);
        paymentRepository.save(tossPayment);

        // Write-Through : Redis에 저장
        String paymentKey = REDIS_PAYMENT_PREFIX + tossPayment.getOrder().getId();

        redisTemplate.opsForValue().set(paymentKey, tossPayment);

        // Cache Store 내에 하루 저장
        // TODO : 결제 내역을 Cache에 얼마나 저장해야하는가?
        redisTemplate.expire(paymentKey, Duration.ofDays(1));

        PaymentResponse paymentResponse = PaymentResponse.from(tossPayment);

        return paymentResponse;
    }

    public PaymentResponse getPayment(Long orderId) {
        String redisKey = REDIS_PAYMENT_PREFIX + String.valueOf(orderId);// Read-Through 전략

        // Read-Through 전략 -> 캐시에서 우선적으로 조회
        TossPayment cachedPayment = (TossPayment) redisTemplate.opsForValue().get(redisKey);
        if (cachedPayment != null) {
            PaymentResponse paymentResponse = PaymentResponse.from(cachedPayment);
            return paymentResponse;
        }

        // Cache Store에 없을 경우 DB에서 조회
        TossPayment dbStoredTossPayment = paymentRepository.findByOrderId(orderId).orElseThrow(() -> new ApiException(PaymentErrorCode.NO_EXIST_PAYMENT));

        // Cache Store에 저장
        redisTemplate.opsForValue().set(redisKey, dbStoredTossPayment);
        PaymentResponse paymentResponse = PaymentResponse.from(dbStoredTossPayment);
        return paymentResponse;
    }


}
