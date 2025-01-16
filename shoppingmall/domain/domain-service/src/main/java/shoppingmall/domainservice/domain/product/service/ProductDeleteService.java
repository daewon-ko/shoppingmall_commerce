package shoppingmall.domainservice.domain.product.service;

import lombok.RequiredArgsConstructor;
import shoppingmall.domainrdb.common.annotation.DomainRdbService;
import shoppingmall.domainrdb.product.service.ProductRdbService;
import shoppingmall.domainservice.common.annotation.DomainService;

@DomainService
@RequiredArgsConstructor
public class ProductDeleteService {
    private final ProductRdbService productRdbService;


    public void deleteProduct(final Long productId) {

        productRdbService.deleteProduct(productId);
    }


}
