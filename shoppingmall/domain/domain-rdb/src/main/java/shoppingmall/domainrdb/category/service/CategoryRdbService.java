package shoppingmall.domainrdb.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import shoppingmall.common.exception.ApiException;
import shoppingmall.common.exception.domain.CategoryErrorCode;
import shoppingmall.domainrdb.category.CategoryDomain;
import shoppingmall.domainrdb.category.entity.Category;
import shoppingmall.domainrdb.category.repository.CategoryRepository;
import shoppingmall.domainrdb.common.annotation.DomainRdbService;
import shoppingmall.domainrdb.mapper.CategoryEntityMapper;

@DomainRdbService
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryRdbService {
    private final CategoryRepository categoryRepository;

    @Transactional
    public long createCategory(final CategoryDomain categoryDomain) {
        Category savedCategory = categoryRepository.save(CategoryEntityMapper.toEntity(categoryDomain));
        return savedCategory.getId();
    }

    public Long findByCategoryName(final String categoryName) {

        Category category = categoryRepository.findByName(categoryName)
                .orElseThrow(() -> new ApiException(CategoryErrorCode.NO_EXIST_CATEGORY));


        return category.getId();
    }

    public Boolean findByCategoryId(final Long categoryId) {
        return categoryRepository.existsById(categoryId);
    }


}
