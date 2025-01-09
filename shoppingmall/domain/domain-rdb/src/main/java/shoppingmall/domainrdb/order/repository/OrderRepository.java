package shoppingmall.domainrdb.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shoppingmall.domainrdb.order.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
