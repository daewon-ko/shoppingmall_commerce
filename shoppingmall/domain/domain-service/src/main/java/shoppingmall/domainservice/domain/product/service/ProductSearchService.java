package shoppingmall.domainservice.domain.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import shoppingmall.domainrdb.category.service.CategoryRdbService;
import shoppingmall.domainrdb.common.annotation.DomainService;
import shoppingmall.domainrdb.product.domain.ProductDomain;
import shoppingmall.domainrdb.product.service.*;
import shoppingmall.domainrdb.product.dto.request.ProductSearchCondition;

@DomainService
@RequiredArgsConstructor
public class ProductSearchService {

    private final ProductRdbService productRdbService;
    private final CategoryRdbService categoryRdbService;

    /**
     * List<Long>형태로 Product들의 Id값을 List로 반환한다.
     *
     * @return
     */
    public Slice<ProductDomain> searchProducts(final ProductSearchCondition productSearchCondition, final Pageable pageable) {

        Long categoryId = productSearchCondition.getCategoryId();


        return productRdbService.getAllProductList(productSearchCondition, pageable);

    }



}
