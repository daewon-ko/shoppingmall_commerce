package shoppingmall.domainservice.domain.product.service;

import lombok.RequiredArgsConstructor;
import shoppingmall.domainrdb.common.annotation.DomainRdbService;
import shoppingmall.domainrdb.product.service.ProductRdbService;
import shoppingmall.domainservice.common.annotation.DomainService;
import shoppingmall.domainservice.domain.product.dto.request.ProductUpdateRequestDto;

@DomainService
@RequiredArgsConstructor
public class ProductUpdateService {
    private final ProductRdbService productRdbService;

    public void updateProduct(final Long productId, final ProductUpdateRequestDto updateRequestDto) {

        productRdbService.updateProduct(productId, updateRequestDto.getName(), updateRequestDto.getPrice());

    }
}
