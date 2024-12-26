package shoppingmall.domain.domain.category.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import shoppingmall.domain.domain.category.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByName(String name);
}
