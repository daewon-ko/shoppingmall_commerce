package shoppingmall.core.domain.category.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import shoppingmall.core.domain.category.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByName(String name);
}
