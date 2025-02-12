package shoppingmall.domainrdb.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import shoppingmall.domainrdb.cart.entity.CartProduct;

import java.util.List;
import java.util.Optional;

public interface CartProductRepository extends JpaRepository<CartProduct, Long> {

    @Query("select cp from CartProduct cp join fetch cp.product join fetch cp.cart where cp.cart.id = :cartId")
    Optional<List<CartProduct>> findAllByCartId(@Param("cartId") Long cartId);

    void deleteAllByCartId(Long cartId);
}
