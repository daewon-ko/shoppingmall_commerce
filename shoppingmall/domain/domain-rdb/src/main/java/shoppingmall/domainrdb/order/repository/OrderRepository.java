package shoppingmall.domainrdb.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import shoppingmall.domainrdb.order.entity.Order;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("select o from Order o JOIN FETCH o.user where o.id = :id")
    Optional<Order> findByIdWithUser(Long id);
}
