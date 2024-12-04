package shppingmall.commerce.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shppingmall.commerce.payment.entity.TossPayment;

public interface PaymentRepository extends JpaRepository<TossPayment, Long> {
}
