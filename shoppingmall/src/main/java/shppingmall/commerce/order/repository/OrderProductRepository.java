package shppingmall.commerce.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import shppingmall.commerce.order.entity.OrderProduct;

import java.util.List;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {

    @Modifying
    @Query("UPDATE OrderProduct op SET op.quantity = :quantity WHERE op.order.id = :orderId AND op.product.id = :productId")
    int updateOrderProductQuantity(@Param("orderId") Long orderId,
                                   @Param("productId") Long productId,
                                   @Param("quantity") int quantity);

}
