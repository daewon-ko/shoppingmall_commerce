package shppingmall.commerce.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shppingmall.commerce.cart.entity.CartProduct;

public interface CartProductRepository extends JpaRepository<CartProduct, Long> {
}
