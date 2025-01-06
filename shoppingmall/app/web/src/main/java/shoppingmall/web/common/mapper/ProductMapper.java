package shoppingmall.web.common.mapper;

import shoppingmall.domainrdb.product.ProductDomain;
import shoppingmall.web.api.product.dto.request.ProductCreateRequestDto;
import shoppingmall.web.api.product.dto.response.ProductCreateResponseDto;

import java.util.List;

public abstract class ProductMapper {


    public static ProductDomain toProduct(final ProductCreateRequestDto requestDto) {
        return new ProductDomain(requestDto.getName(), requestDto.getPrice(), CategoryMapper.toCategoryDomain(requestDto.getCagegoryName()), UserMapper.toUserDomain(requestDto.getSellerEmail()));
    }


    public static ProductCreateResponseDto toResponseDto(final ProductDomain productDomain, final Long productId, final List<Long> imageIds) {
        return ProductCreateResponseDto.builder()
                .id(productId)
                .name(productDomain.getName())
                .price(productDomain.getPrice())
                .categoryName(productDomain.getCategoryDomain().getName())
                .imageIds(imageIds)
                .build();
    }

}
