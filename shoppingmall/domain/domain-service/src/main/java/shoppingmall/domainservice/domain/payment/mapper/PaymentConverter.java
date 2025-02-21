package shoppingmall.domainservice.domain.payment.mapper;

import org.springframework.stereotype.Component;
import shoppingmall.domainrdb.payment.TossPaymentDomain;
import shoppingmall.domainrdb.payment.TossPaymentId;
import shoppingmall.domainredis.domain.payment.dto.PaymentCacheDto;
import shoppingmall.tosspayment.feign.TossPaymentMethod;
import shoppingmall.tosspayment.feign.TossPaymentStatus;

@Component
public class PaymentConverter {
   public PaymentCacheDto from(final TossPaymentDomain tossPaymentDomain) {
        return PaymentCacheDto.builder()
                .paymentId(tossPaymentDomain.getTossPaymentId().getValue())
                .paymentKey(tossPaymentDomain.getPaymentKey())
                .orderId(tossPaymentDomain.getOrderId())
                .tossPaymentOrderId(tossPaymentDomain.getTossPaymentOrderId())
                .paymentMethod(tossPaymentDomain.getTossPaymentMethod().name())
                .paymentStatus(tossPaymentDomain.getTossPaymentStatus().name())
                .amount(tossPaymentDomain.getAmount())
                .requestedAt(tossPaymentDomain.getRequestedAt())
                .approvedAt(tossPaymentDomain.getApprovedAt())
                .build();
    }

    public TossPaymentDomain from(PaymentCacheDto paymentCacheDto) {
        return TossPaymentDomain.createForRead(
                TossPaymentId.from(paymentCacheDto.getPaymentId()),
                paymentCacheDto.getPaymentKey(),
                String.valueOf(paymentCacheDto.getOrderId()),
                paymentCacheDto.getAmount(),
                paymentCacheDto.getOrderId(),
                TossPaymentStatus.valueOf(paymentCacheDto.getPaymentStatus()),
                TossPaymentMethod.valueOf(paymentCacheDto.getPaymentMethod()),
                paymentCacheDto.getRequestedAt(),
                paymentCacheDto.getApprovedAt()
        );
    }

}
