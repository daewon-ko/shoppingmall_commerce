package shppingmall.commerce.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shppingmall.commerce.order.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
