package shoppingmall.domain.domain.cart.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import shoppingmall.domain.domain.cart.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
