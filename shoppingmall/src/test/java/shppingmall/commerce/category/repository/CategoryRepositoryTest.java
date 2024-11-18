package shppingmall.commerce.category.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import shppingmall.commerce.category.entity.Category;
import shppingmall.commerce.support.RepositoryTestSupport;

import java.util.List;

class CategoryRepositoryTest extends RepositoryTestSupport {

    @Autowired
    private CategoryRepository categoryRepository;

    @DisplayName("카테고리 이름으로 카테고리를 조회한다.")
    @Test
    void findCategoryByName() {
        //given
        Category category1 = getCategory("카테고리A");

        categoryRepository.save(category1);
        //when
        Category findCategory = categoryRepository.findByName(category1.getName());


        //then
        Assertions.assertThat(findCategory)
                .extracting("name")
                .isEqualTo(category1.getName());
    }

    private Category getCategory(String name) {
        Category category = Category
                .builder()
                .name(name)
                .build();
        categoryRepository.save(category);
        return category;
    }


}