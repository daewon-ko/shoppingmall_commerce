package shoppingmall.core.domain.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shoppingmall.core.domain.order.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
