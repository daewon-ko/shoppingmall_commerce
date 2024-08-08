package shppingmall.commerce.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shppingmall.commerce.category.entity.Category;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByName(String name);
}
