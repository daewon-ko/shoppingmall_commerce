package shoppingmall.domainservice.domain.product.mapper;

import shoppingmall.domainrdb.product.domain.ProductDomain;
import shoppingmall.domainservice.domain.image.dto.response.ImageResponseDto;
import shoppingmall.domainservice.domain.product.dto.response.ProductQueryResponseDto;

import java.util.List;

public abstract class ProductDtoMapper {





    public static ProductQueryResponseDto toSearchResponseDto(final ProductDomain productDomain, final List<ImageResponseDto> imageResponseDtos) {
        return ProductQueryResponseDto.of(productDomain, imageResponseDtos);
    }

}
