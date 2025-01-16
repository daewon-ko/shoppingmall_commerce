package shoppingmall.domainrdb.payment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shoppingmall.common.exception.ApiException;
import shoppingmall.common.exception.domain.PaymentErrorCode;
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


    //TODO : N+1 터지지 않을까?

    @Transactional
    public TossPaymentDomain savePayment(final TossPaymentDomain tossPaymentDomain) {
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

        return TossPaymentDomain.createForRead(savedPayment);
    }

    public TossPaymentDomain getPayment(Long orderId) {


        // Cache Store에 없을 경우 DB에서 조회
        TossPayment dbStoredTossPayment = paymentRepository.findByOrderId(orderId).orElseThrow(() -> new ApiException(PaymentErrorCode.NO_EXIST_PAYMENT));


        return TossPaymentDomain.createForRead(dbStoredTossPayment);

    }


}
