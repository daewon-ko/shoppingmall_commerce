package shppingmall.commerce.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shppingmall.commerce.payment.entity.TossPayment;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<TossPayment, Long> {

    Optional<TossPayment> findByOrderId(Long orderId);
}
