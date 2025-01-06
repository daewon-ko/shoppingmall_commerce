package shoppingmall.web.api.category.usecase;

import lombok.RequiredArgsConstructor;
import shoppingmall.domainrdb.category.service.CategoryRdbService;
import shoppingmall.web.api.category.dto.request.CategoryRequestDto;
import shoppingmall.web.common.annotataion.Usecase;

@RequiredArgsConstructor
@Usecase
public class CategoryUsecase {
    private final CategoryRdbService categoryRdbService;


    public void createCategory(final CategoryRequestDto categoryRequestDto) {
        categoryRdbService.createCategory(categoryRequestDto.getCategoryName());
    }

}
