package shppingmall.commerce.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shppingmall.commerce.order.entity.OrderProduct;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {
}
