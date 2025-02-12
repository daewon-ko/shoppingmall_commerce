package shoppingmall.domainservice.domain.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import shoppingmall.domainrdb.common.annotation.DomainRdbService;
import shoppingmall.domainrdb.image.ImageDomain;
import shoppingmall.domainrdb.product.domain.ProductDomain;
import shoppingmall.domainrdb.product.service.*;
import shoppingmall.domainrdb.product.dto.request.ProductSearchCondition;
import shoppingmall.domainservice.common.annotation.DomainService;
import shoppingmall.domainservice.domain.image.mapper.ImageDtoMapper;
import shoppingmall.domainservice.domain.product.dto.request.ProductSearchConditionRequestDto;
import shoppingmall.domainservice.domain.product.dto.response.ProductQueryResponseDto;
import shoppingmall.domainservice.domain.product.mapper.ProductDtoMapper;

import java.util.ArrayList;
import java.util.List;

@DomainService
@RequiredArgsConstructor
public class ProductSearchService {

    private final ProductRdbService productRdbService;
    private final ProductImageService productImageService;

    /**
     * List<Long>형태로 Product들의 Id값을 List로 반환한다.
     *
     * @return
     */
    public Slice<ProductQueryResponseDto> searchProducts(final ProductSearchConditionRequestDto productSearchConditionRequestDto, final Pageable pageable) {

        ProductSearchCondition productSearchCondition = ProductSearchCondition.builder()
                .categoryId(productSearchConditionRequestDto.getCategoryId())
                .minPrice(productSearchConditionRequestDto.getMinPrice())
                .maxPrice(productSearchConditionRequestDto.getMaxPrice())
                .fileTypeList(productSearchConditionRequestDto.getFileTypes())
                .startDate(productSearchConditionRequestDto.getStartDate())
                .endDate(productSearchConditionRequestDto.getEndDate())
                .productName(productSearchConditionRequestDto.getProductName())
                .build();



        Slice<ProductDomain> productDomains = productRdbService.getAllProductList(productSearchCondition, pageable);

        List<ProductQueryResponseDto> listDtos = new ArrayList<>();
        for (ProductDomain productDomain : productDomains) {

            List<ImageDomain> imageDomains = productImageService.searchProductImages(productDomain.getProductId().getValue(), productSearchConditionRequestDto.getFileTypes());

            listDtos.add(ProductDtoMapper.toSearchResponseDto(productDomain, ImageDtoMapper.toImageResponseDto(imageDomains)));


        }

        return new SliceImpl<>(listDtos, pageable, productDomains.hasNext());


    }



}
