package shoppingmall.domainrdb.category.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import shoppingmall.domainrdb.category.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByName(String name);
}
