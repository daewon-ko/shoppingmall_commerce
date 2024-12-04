package shppingmall.commerce.payment.entity;

import jakarta.persistence.*;
import lombok.*;
import shppingmall.commerce.order.entity.Order;
import shppingmall.commerce.payment.dto.PaymentConfirmResponse;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class TossPayment {

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

    @OneToOne
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



    public static TossPayment of(final PaymentConfirmResponse paymentConfirmResponse, final Order order) {
        return TossPayment.builder()
                .order(order)
                .tossPaymentMethod(TossPaymentMethod.valueOf(paymentConfirmResponse.getMethod()))
                .tossPaymentOrderId(paymentConfirmResponse.getOrderId())
                .tossPaymentStatus(TossPaymentStatus.valueOf(paymentConfirmResponse.getStatus()))
                .amount(paymentConfirmResponse.getTotalAmount())
                .approvedAt(OffsetDateTime.parse(paymentConfirmResponse.getApprovedAt()).toLocalDateTime())
                .requestedAt(OffsetDateTime.parse(paymentConfirmResponse.getRequestedAt()).toLocalDateTime())
                .paymentKey(paymentConfirmResponse.getPaymentKey())
                .build();
    }



}
