package shoppingmall.domainredis.domain.payment.dto;

import lombok.*;
import org.springframework.cglib.core.Local;
import shoppingmall.common.dto.toss.TossPaymentConfirmResponse;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * 결제 정보 Cache DTO
 */
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentCacheDto implements Serializable {

    private Long orderId;
    private Long paymentId;
    private String paymentKey;
    private String tossPaymentOrderId;
    private String paymentStatus;
    private Long amount;
    private String paymentMethod;
    private LocalDateTime requestedAt;
    private LocalDateTime approvedAt;

    @Builder
    private PaymentCacheDto(Long orderId, Long paymentId, String paymentKey, String tossPaymentOrderId, String paymentStatus, Long amount, String paymentMethod, LocalDateTime requestedAt, LocalDateTime approvedAt) {
        this.orderId = orderId;
        this.paymentId = paymentId;
        this.paymentKey = paymentKey;
        this.tossPaymentOrderId = tossPaymentOrderId;
        this.paymentStatus = paymentStatus;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.requestedAt = requestedAt;
        this.approvedAt = approvedAt;
    }
}
