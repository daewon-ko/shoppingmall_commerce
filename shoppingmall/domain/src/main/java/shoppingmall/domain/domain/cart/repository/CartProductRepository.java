package shoppingmall.domain.domain.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shoppingmall.domain.domain.cart.entity.CartProduct;

public interface CartProductRepository extends JpaRepository<CartProduct, Long> {
}
