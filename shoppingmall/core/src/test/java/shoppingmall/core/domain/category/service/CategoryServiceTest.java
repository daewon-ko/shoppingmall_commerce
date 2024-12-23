package shoppingmall.core.domain.category.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import shoppingmall.core.domain.category.dto.request.CategoryRequestDto;
import shoppingmall.core.domain.category.repository.CategoryRepository;
import shoppingmall.core.support.IntegrationTestSupport;

import static org.assertj.core.api.Assertions.*;


class CategoryServiceTest extends IntegrationTestSupport {

    @Autowired
    CategoryService categoryService;
    @Autowired
    CategoryRepository categoryRepository;

    @DisplayName("카테고리 이름을 받아서 카테고리를 생성한다. ")
    @Test
    void createCategory() {
        //given
        CategoryRequestDto request = CategoryRequestDto.builder()
                .categoryName("카테고리")
                .build();
        //when
        long savedCategoryId = categoryService.createCategory(request);

        //then
        assertThat(categoryRepository.findByName(request.getCategoryName()).getId())
                .isEqualTo(savedCategoryId);

    }


}