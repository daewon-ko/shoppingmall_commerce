package shoppingmall.domainrdb.payment.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shoppingmall.common.dto.TossPaymentConfirmResponse;
import shoppingmall.domainrdb.domain.order.entity.Order;
import shoppingmall.tosspayment.feign.TossPaymentMethod;
import shoppingmall.tosspayment.feign.TossPaymentStatus;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@ToString
public class TossPayment implements Serializable {

    @Serial
    private static final long serialVersionUID = -3618019291381378495L;

    // TODO : Long형이 과연 적절할까? ID값으로?
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;
    @Column(nullable = false, unique = true)
    private String paymentKey;

    @Column(nullable = false)
    private String tossPaymentOrderId;

    @Column
    private Long amount;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private TossPaymentStatus tossPaymentStatus;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private TossPaymentMethod tossPaymentMethod;

    @Column(nullable = false)
    private LocalDateTime requestedAt;

    @Column
    private LocalDateTime approvedAt;


    @Builder
    private TossPayment(Long paymentId, String paymentKey, String tossPaymentOrderId, Long amount, Order order, TossPaymentStatus tossPaymentStatus, TossPaymentMethod tossPaymentMethod, LocalDateTime requestedAt, LocalDateTime approvedAt) {
        this.paymentId = paymentId;
        this.paymentKey = paymentKey;
        this.tossPaymentOrderId = tossPaymentOrderId;
        this.amount = amount;
        this.order = order;
        this.tossPaymentStatus = tossPaymentStatus;
        this.tossPaymentMethod = tossPaymentMethod;
        this.requestedAt = requestedAt;
        this.approvedAt = approvedAt;
    }



    public static TossPayment of(final TossPaymentConfirmResponse tossPaymentConfirmResponse, final Order order) {
        return TossPayment.builder()
                .order(order)
                .tossPaymentMethod(TossPaymentMethod.valueOf(tossPaymentConfirmResponse.getMethod()))
                .tossPaymentOrderId(tossPaymentConfirmResponse.getOrderId())
                .tossPaymentStatus(TossPaymentStatus.valueOf(tossPaymentConfirmResponse.getStatus()))
                .amount(tossPaymentConfirmResponse.getTotalAmount())
                .approvedAt(OffsetDateTime.parse(tossPaymentConfirmResponse.getApprovedAt()).toLocalDateTime())
                .requestedAt(OffsetDateTime.parse(tossPaymentConfirmResponse.getRequestedAt()).toLocalDateTime())
                .paymentKey(tossPaymentConfirmResponse.getPaymentKey())
                .build();
    }



}
