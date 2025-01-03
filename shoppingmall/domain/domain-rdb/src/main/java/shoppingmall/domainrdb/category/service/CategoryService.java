package shoppingmall.domainrdb.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import shoppingmall.domainrdb.category.entity.Category;
import shoppingmall.domainrdb.category.repository.CategoryRepository;
import shoppingmall.domainrdb.common.annotation.DomainService;

@DomainService
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Transactional
    public long createCategory(final String categoryName) {

        Category category = Category.builder().name(categoryName).build();
        Category savedCategory = categoryRepository.save(category);

        return savedCategory.getId();

    }
}
