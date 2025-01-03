package shoppingmall.web.api.category.usecase;

import lombok.RequiredArgsConstructor;
import shoppingmall.domainrdb.category.service.CategoryService;
import shoppingmall.web.api.category.dto.request.CategoryRequestDto;
import shoppingmall.web.common.annotataion.Usecase;

@RequiredArgsConstructor
@Usecase
public class CategoryUsecase {
    private final CategoryService categoryService;


    public void createCategory(final CategoryRequestDto categoryRequestDto) {
        categoryService.createCategory(categoryRequestDto.getCategoryName());
    }

}
