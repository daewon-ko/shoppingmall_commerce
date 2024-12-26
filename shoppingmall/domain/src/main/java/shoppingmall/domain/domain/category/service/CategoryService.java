package shoppingmall.domain.domain.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shoppingmall.domain.domain.category.dto.request.CategoryRequestDto;
import shoppingmall.domain.domain.category.entity.Category;
import shoppingmall.domain.domain.category.repository.CategoryRepository;

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
