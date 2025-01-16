package shoppingmall.domainrdb.payment;

import lombok.Getter;
import shoppingmall.common.dto.toss.TossPaymentConfirmResponse;
import shoppingmall.domainrdb.order.domain.OrderDomain;
import shoppingmall.domainrdb.payment.entity.TossPayment;
import shoppingmall.tosspayment.feign.TossPaymentMethod;
import shoppingmall.tosspayment.feign.TossPaymentStatus;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Getter
public class TossPaymentDomain {
    private final TossPaymentId tossPaymentId;
    private final String paymentKey;
    private final String tossPaymentOrderId;
    private final Long amount;
    private final Long orderId;
    private final TossPaymentStatus tossPaymentStatus;
    private final TossPaymentMethod tossPaymentMethod;
    private final LocalDateTime requestedAt;
    private final LocalDateTime approvedAt;

    private TossPaymentDomain(TossPaymentId tossPaymentId, String paymentKey, String tossPaymentOrderId, Long amount, Long orderId,
                              TossPaymentStatus tossPaymentStatus, TossPaymentMethod tossPaymentMethod,
                              LocalDateTime requestedAt, LocalDateTime approvedAt) {
        this.tossPaymentId = tossPaymentId;
        this.paymentKey = paymentKey;
        this.tossPaymentOrderId = tossPaymentOrderId;
        this.amount = amount;
        this.orderId = orderId;
        this.tossPaymentStatus = tossPaymentStatus;
        this.tossPaymentMethod = tossPaymentMethod;
        this.requestedAt = requestedAt;
        this.approvedAt = approvedAt;
    }

    public static TossPaymentDomain createForRead(final TossPayment tossPayment) {
        return new TossPaymentDomain(
                TossPaymentId.from(tossPayment.getPaymentId()),
                tossPayment.getPaymentKey(),
                tossPayment.getTossPaymentOrderId(),
                tossPayment.getAmount(),
                tossPayment.getOrder().getId(),
                tossPayment.getTossPaymentStatus(),
                tossPayment.getTossPaymentMethod(),
                tossPayment.getRequestedAt(),
                tossPayment.getApprovedAt()
        );
    }

    public static TossPaymentDomain createForRead(TossPaymentId tossPaymentId, String paymentKey, String tossPaymentOrderId, Long amount, Long orderId,
                                                  TossPaymentStatus tossPaymentStatus, TossPaymentMethod tossPaymentMethod,
                                                  LocalDateTime requestedAt, LocalDateTime approvedAt) {
        return new TossPaymentDomain(tossPaymentId, paymentKey, tossPaymentOrderId, amount, orderId, tossPaymentStatus, tossPaymentMethod, requestedAt, approvedAt);
    }


    public static TossPaymentDomain createForWrite(final TossPaymentConfirmResponse tossPaymentConfirmResponse, final OrderDomain orderDomain) {
        return new TossPaymentDomain(
                null,
                tossPaymentConfirmResponse.getPaymentKey(),
                tossPaymentConfirmResponse.getOrderId(),
                tossPaymentConfirmResponse.getTotalAmount(),
                orderDomain.getOrderId().getValue(),
                TossPaymentStatus.valueOf(tossPaymentConfirmResponse.getStatus()),
                TossPaymentMethod.valueOf(tossPaymentConfirmResponse.getMethod()),
                LocalDateTime.parse(tossPaymentConfirmResponse.getRequestedAt()),
                LocalDateTime.parse(tossPaymentConfirmResponse.getApprovedAt())
        );
    }


}
