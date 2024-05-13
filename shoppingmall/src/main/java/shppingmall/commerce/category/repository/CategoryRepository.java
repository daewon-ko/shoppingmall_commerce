package shppingmall.commerce.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shppingmall.commerce.category.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
