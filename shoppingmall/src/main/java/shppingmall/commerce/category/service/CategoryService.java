package shppingmall.commerce.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shppingmall.commerce.category.dto.CategoryRequestDto;
import shppingmall.commerce.category.entity.Category;
import shppingmall.commerce.category.repository.CategoryRepository;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    public void createCategory(final CategoryRequestDto categoryRequestDto) {
        Category category = categoryRequestDto.toEntity();
        categoryRepository.save(category);
    }
}
