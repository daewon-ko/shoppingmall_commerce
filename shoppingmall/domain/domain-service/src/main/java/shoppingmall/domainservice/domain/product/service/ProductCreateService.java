package shoppingmall.domainservice.domain.product.service;

import lombok.RequiredArgsConstructor;
import shoppingmall.domainrdb.category.service.CategoryId;
import shoppingmall.domainrdb.product.domain.ProductDomain;
import shoppingmall.domainrdb.category.service.CategoryRdbService;
import shoppingmall.domainrdb.common.annotation.DomainService;
import shoppingmall.domainrdb.product.service.ProductRdbService;
import shoppingmall.domainrdb.user.UserId;
import shoppingmall.domainrdb.user.service.UserRdbService;
import shoppingmall.domainservice.domain.product.dto.request.ProductCreateRequestDto;

@DomainService
@RequiredArgsConstructor
public class ProductCreateService {
    private final ProductRdbService productRdbService;
    private final CategoryRdbService categoryRdbService;
    private final UserRdbService userRdbService;


    public Long createProduct(final ProductCreateRequestDto createRequestDto) {

        // 중복 category 검증
        Long categoryId = categoryRdbService.findByCategoryName(createRequestDto.getCagegoryName());

        // email로 회원 존재여부 검증
        Long userId = userRdbService.findSellerByEmail(createRequestDto.getSellerEmail());

        ProductDomain productDomain = ProductDomain.createForWrite(createRequestDto.getName(), createRequestDto.getPrice(), CategoryId.from(categoryId), UserId.from(userId));

        // Domain 객체 -> Entity 변환해서 매개변수로 넘겨준다.
        return productRdbService.createProduct(productDomain);
    }

}
