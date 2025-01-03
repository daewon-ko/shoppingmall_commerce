package shoppingmall.domainrdb.cart.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import shoppingmall.domainrdb.cart.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
