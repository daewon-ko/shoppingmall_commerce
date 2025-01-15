package shoppingmall.domainservice.domain.product.service;

import lombok.RequiredArgsConstructor;
import shoppingmall.domainrdb.common.annotation.DomainService;
import shoppingmall.domainrdb.product.domain.ProductDomain;
import shoppingmall.domainrdb.product.service.ProductRdbService;

@DomainService
@RequiredArgsConstructor
public class ProductUpdateService {
    private final ProductRdbService productRdbService;

    public void updateProduct(final ProductDomain productDomain) {

        productRdbService.updateProduct(productDomain);

    }
}
