package shoppingmall.domainrdb.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import shoppingmall.domainrdb.category.CategoryDomain;
import shoppingmall.domainrdb.category.entity.Category;
import shoppingmall.domainrdb.category.repository.CategoryRepository;
import shoppingmall.domainrdb.common.annotation.DomainService;
import shoppingmall.domainrdb.mapper.CategoryEntityMapper;

import java.util.Optional;

@DomainService
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryRdbService {
    private final CategoryRepository categoryRepository;

    @Transactional
    public long createCategory(final CategoryDomain categoryDomain) {
        Category savedCategory = categoryRepository.save(CategoryEntityMapper.toEntity(categoryDomain));
        return savedCategory.getId();
    }

    public Boolean findByCategoryName(final String categoryName) {

        return categoryRepository.existsByName(categoryName);
    }

    public Boolean findByCategoryId(final Long categoryId) {
        return categoryRepository.existsById(categoryId);
    }


}
