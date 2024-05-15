package shppingmall.commerce.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shppingmall.commerce.cart.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
