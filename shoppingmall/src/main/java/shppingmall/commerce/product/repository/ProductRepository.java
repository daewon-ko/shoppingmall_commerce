package shppingmall.commerce.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shppingmall.commerce.product.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
