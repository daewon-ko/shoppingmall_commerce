package shoppingmall.domainservice.domain.product.service;

import lombok.RequiredArgsConstructor;
import shoppingmall.domainrdb.common.annotation.DomainService;
import shoppingmall.domainrdb.product.domain.ProductDomain;
import shoppingmall.domainrdb.product.service.ProductRdbService;
import shoppingmall.domainservice.domain.product.dto.request.ProductUpdateRequestDto;
import shoppingmall.domainservice.domain.product.mapper.ProductDtoMapper;

@DomainService
@RequiredArgsConstructor
public class ProductUpdateService {
    private final ProductRdbService productRdbService;

    public void updateProduct(final Long productId, final ProductUpdateRequestDto updateRequestDto) {

        productRdbService.updateProduct(productId, updateRequestDto.getName(), updateRequestDto.getPrice());

    }
}
