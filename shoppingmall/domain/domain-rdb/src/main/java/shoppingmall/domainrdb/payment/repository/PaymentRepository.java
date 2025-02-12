package shoppingmall.domainrdb.payment.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import shoppingmall.domainrdb.payment.entity.TossPayment;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<TossPayment, Long> {

    Optional<TossPayment> findByOrderId(Long orderId);
}
