package shoppingmall.domainservice.domain.product.service;

import lombok.RequiredArgsConstructor;
import shoppingmall.domainrdb.category.CategoryDomain;
import shoppingmall.domainrdb.product.ProductDomain;
import shoppingmall.domainrdb.user.UserDomain;
import shoppingmall.common.exception.ApiException;
import shoppingmall.common.exception.domain.CategoryErrorCode;
import shoppingmall.common.exception.domain.UserErrorCode;
import shoppingmall.domainrdb.category.service.CategoryRdbService;
import shoppingmall.domainrdb.common.annotation.DomainService;
import shoppingmall.domainrdb.product.service.ProductRdbService;
import shoppingmall.domainrdb.user.service.UserRdbService;

@DomainService
@RequiredArgsConstructor
public class ProductCreateService {
    private final ProductRdbService productRdbService;
    private final CategoryRdbService categoryRdbService;
    private final UserRdbService userRdbService;




    public Long createProduct(final ProductDomain productDomain) {
        // Validation
        CategoryDomain categoryDomain = productDomain.getCategoryDomain();
        if (categoryDomain.getName() == null || categoryDomain.getName().isEmpty()) {
            throw new IllegalArgumentException("Category name is empty");
        }

        // 중복 category 검증
        if (categoryRdbService.findByCategoryName(categoryDomain.getName())) {
            throw new ApiException(CategoryErrorCode.DUPLICATED_CATEGORY);
        }


        UserDomain userDomain = productDomain.getUserDomain();
        //validation
        if (userDomain.getEmail() == null || userDomain.getEmail().isEmpty()) {
            throw new IllegalArgumentException("User email is empty");
        }
        // email 존재여부 검증
        if (!userRdbService.isExistEmail(userDomain.getEmail())) {
            // email이 없으면 예외를 던진다.
            throw new ApiException(UserErrorCode.NO_EXIST_USER);
        }

        // Domain 객체 -> Entity 변환해서 매개변수로 넘겨준다.
        return productRdbService.createProduct(productDomain);
    }

}
