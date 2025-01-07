package shoppingmall.web.common.mapper;

import shoppingmall.domainrdb.category.CategoryDomain;
import shoppingmall.domainrdb.image.ImageDomain;
import shoppingmall.domainrdb.product.ProductDomain;
import shoppingmall.domainrdb.product.dto.request.ProductSearchCondition;
import shoppingmall.domainrdb.user.UserDomain;
import shoppingmall.web.api.image.dto.response.ImageResponseDto;
import shoppingmall.web.api.product.dto.request.ProductCreateRequestDto;
import shoppingmall.web.api.product.dto.request.ProductSearchConditionRequestDto;
import shoppingmall.web.api.product.dto.response.ProductCreateResponseDto;
import shoppingmall.web.api.product.dto.response.ProductQueryResponseDto;

import java.util.List;

public abstract class ProductDtoMapper {


    public static ProductDomain toProductDomain(final ProductCreateRequestDto requestDto) {

        return ProductDomain.createForWrite(requestDto.getName(), requestDto.getPrice(), new CategoryDomain(requestDto.getCagegoryName()), new UserDomain(null, requestDto.getSellerEmail()));
    }


    public static ProductCreateResponseDto toCreateResponseDto(final ProductDomain productDomain, final Long productId, final List<Long> imageIds) {
        return ProductCreateResponseDto.builder()
                .id(productId)
                .name(productDomain.getName())
                .price(productDomain.getPrice())
                .categoryName(productDomain.getCategoryDomain().getName())
                .imageIds(imageIds)
                .build();
    }

    public static ProductSearchCondition toSearchCondition(final ProductSearchConditionRequestDto productSearchConditionRequestDto) {
        return ProductSearchCondition.builder()
                .categoryId(productSearchConditionRequestDto.getCategoryId())
                .minPrice(productSearchConditionRequestDto.getMinPrice())
                .maxPrice(productSearchConditionRequestDto.getMaxPrice())
                .fileTypeList(productSearchConditionRequestDto.getFileTypes())
                .startDate(productSearchConditionRequestDto.getStartDate())
                .endDate(productSearchConditionRequestDto.getEndDate())
                .productName(productSearchConditionRequestDto.getProductName())
                .build();

    }

    public static ProductQueryResponseDto toSearchResponseDto(final ProductDomain productDomain, final List<ImageResponseDto> imageResponseDtos, final Long categoryId) {
        return ProductQueryResponseDto.of(productDomain, imageResponseDtos);
    }

}
