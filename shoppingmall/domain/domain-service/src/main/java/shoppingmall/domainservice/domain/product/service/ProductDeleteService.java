package shoppingmall.domainservice.domain.product.service;

import lombok.RequiredArgsConstructor;
import shoppingmall.domainrdb.common.annotation.DomainRdbService;
import shoppingmall.domainrdb.image.entity.FileType;
import shoppingmall.domainrdb.image.entity.Image;
import shoppingmall.domainrdb.image.service.ImageRdbService;
import shoppingmall.domainrdb.product.service.ProductRdbService;
import shoppingmall.domainservice.common.annotation.DomainService;
import shoppingmall.domainservice.domain.image.service.ImageDeleteService;

import java.time.LocalDateTime;
import java.util.List;

@DomainService
@RequiredArgsConstructor
public class ProductDeleteService {
    private final ProductRdbService productRdbService;


    public void deleteProduct(final Long productId) {
        productRdbService.deleteProduct(productId);
    }


}
