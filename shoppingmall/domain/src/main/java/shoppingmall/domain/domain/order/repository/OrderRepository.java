package shoppingmall.domain.domain.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shoppingmall.domain.domain.order.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
