package shoppingmall.web.api.category.usecase;

import lombok.RequiredArgsConstructor;
import shoppingmall.domainrdb.category.mapper.CategoryMapper;
import shoppingmall.domainrdb.category.service.CategoryRdbService;
import shoppingmall.web.api.category.dto.request.CategoryRequestDto;
import shoppingmall.web.common.annotataion.Usecase;
import shoppingmall.web.common.mapper.CategoryDtoMapper;

@RequiredArgsConstructor
@Usecase
public class CategoryUsecase {
    private final CategoryRdbService categoryRdbService;


    public void createCategory(final CategoryRequestDto categoryRequestDto) {
        categoryRdbService.createCategory(CategoryDtoMapper.toCategoryDomain(categoryRequestDto.getCategoryName()));
    }

}
