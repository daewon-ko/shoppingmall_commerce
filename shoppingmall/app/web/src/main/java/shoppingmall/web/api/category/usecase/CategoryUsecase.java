package shoppingmall.web.api.category.usecase;

import lombok.RequiredArgsConstructor;
import shoppingmall.common.annotation.Usecase;
import shoppingmall.domainrdb.category.service.CategoryService;

@Usecase
@RequiredArgsConstructor
public class CategoryUsecase {
    private final CategoryService categoryService;

}
