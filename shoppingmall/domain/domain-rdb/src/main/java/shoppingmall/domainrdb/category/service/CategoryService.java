package shoppingmall.domainrdb.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shoppingmall.domainrdb.category.entity.Category;
import shoppingmall.domainrdb.category.repository.CategoryRepository;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Transactional
    public long createCategory(final CategoryRequestDto categoryRequestDto) {
        Category category = categoryRequestDto.toEntity();
        Category savedCategory = categoryRepository.save(category);

        return savedCategory.getId();

    }
}
