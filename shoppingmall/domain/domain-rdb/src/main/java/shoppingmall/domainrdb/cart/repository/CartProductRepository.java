package shoppingmall.domainrdb.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shoppingmall.domainrdb.cart.entity.CartProduct;

public interface CartProductRepository extends JpaRepository<CartProduct, Long> {
}
