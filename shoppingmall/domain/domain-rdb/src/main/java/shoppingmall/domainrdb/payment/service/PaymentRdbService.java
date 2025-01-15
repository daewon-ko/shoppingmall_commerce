package shoppingmall.domainrdb.payment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shoppingmall.domainrdb.common.annotation.DomainRdbService;
import shoppingmall.domainrdb.order.repository.OrderRepository;
import shoppingmall.domainrdb.payment.TossPaymentDomain;
import shoppingmall.domainrdb.payment.entity.TossPayment;
import shoppingmall.domainrdb.payment.repository.PaymentRepository;

@DomainRdbService
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class PaymentRdbService {

    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;







    @Transactional
    public Long savePayment(final TossPaymentDomain tossPaymentDomain) {
        TossPayment tossPayment = TossPayment.builder()
                .paymentKey(tossPaymentDomain.getPaymentKey())
                .tossPaymentOrderId(tossPaymentDomain.getTossPaymentOrderId())
                .amount(tossPaymentDomain.getAmount())
                .order(orderRepository.getReferenceById(tossPaymentDomain.getOrderId()))
                .tossPaymentStatus(tossPaymentDomain.getTossPaymentStatus())
                .tossPaymentMethod(tossPaymentDomain.getTossPaymentMethod())
                .requestedAt(tossPaymentDomain.getRequestedAt())
                .approvedAt(tossPaymentDomain.getApprovedAt())
                .build();

        TossPayment savedPayment = paymentRepository.save(tossPayment);
        return savedPayment.getPaymentId();
//
//
//        PaymentResponse paymentResponse = PaymentResponse.from(tossPayment);
//
//        return paymentResponse;
    }

//    public PaymentResponse getPayment(Long orderId) {
//        String redisKey = REDIS_PAYMENT_PREFIX + String.valueOf(orderId);// Read-Through 전략
//
//        // Read-Through 전략 -> 캐시에서 우선적으로 조회
//        TossPayment cachedPayment = (TossPayment) redisTemplate.opsForValue().get(redisKey);
//        if (cachedPayment != null) {
//            PaymentResponse paymentResponse = PaymentResponse.from(cachedPayment);
//            return paymentResponse;
//        }
//
//        // Cache Store에 없을 경우 DB에서 조회
//        TossPayment dbStoredTossPayment = paymentRepository.findByOrderId(orderId).orElseThrow(() -> new ApiException(PaymentErrorCode.NO_EXIST_PAYMENT));
//
//        // Cache Store에 저장
//        redisTemplate.opsForValue().set(redisKey, dbStoredTossPayment);
//        PaymentResponse paymentResponse = PaymentResponse.from(dbStoredTossPayment);
//        return paymentResponse;
//    }


}
