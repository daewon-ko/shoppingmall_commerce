package shoppingmall.web.api.product.usecase;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import shoppingmall.domainservice.domain.product.dto.request.ProductCreateRequestDto;
import shoppingmall.domainservice.domain.product.dto.request.ProductSearchConditionRequestDto;
import shoppingmall.domainservice.domain.product.dto.request.ProductUpdateRequestDto;
import shoppingmall.domainservice.domain.product.dto.response.ProductCreateResponseDto;
import shoppingmall.domainservice.domain.product.dto.response.ProductQueryResponseDto;
import shoppingmall.domainservice.domain.product.service.*;
import shoppingmall.web.common.annotataion.Usecase;
import shoppingmall.web.common.validation.product.ProductCreateValidator;
import shoppingmall.web.common.validation.product.ProductSearchValidator;

import java.util.ArrayList;
import java.util.List;

@Usecase
@RequiredArgsConstructor
public class ProductUsecase {
    private final ProductCreateService productCreateService;
    private final ProductImageService productImageService;
    private final ProductSearchService productSearchService;
    private final ProductUpdateService productUpdateService;
    private final ProductDeleteService productDeleteService;
    private final ProductCreateValidator productCreateValidator;
    private final ProductSearchValidator productSearchValidator;


    @Transactional
    public ProductCreateResponseDto createProduct(final ProductCreateRequestDto productCreateRequestDto, final List<MultipartFile> multipartFiles) {

        // ProductCreateRequest Validation
        productCreateValidator.validate(productCreateRequestDto);


        Long productId = productCreateService.createProduct(productCreateRequestDto);

        final List<Long> imageIds = productImageService.saveProductImages(multipartFiles, productId);


        return ProductCreateResponseDto.builder()
                .id(productId)
                .name(productCreateRequestDto.getName())
                .price(productCreateRequestDto.getPrice())
                .categoryName(productCreateRequestDto.getCagegoryName())
                .imageIds(imageIds).build();

    }

    public Slice<ProductQueryResponseDto> getAllProductList(final ProductSearchConditionRequestDto searchConditionRequestDto, final Pageable pageable) {

        // ProductSearchConditionRequestDto Validation
        productSearchValidator.validate(searchConditionRequestDto);


        return productSearchService.searchProducts(searchConditionRequestDto, pageable);


    }


    public void updateProducts(final Long productId, final ProductUpdateRequestDto updateRequestDto) {
        productUpdateService.updateProduct(productId, updateRequestDto);
    }

    public void updateProductThumbNailImage(final Long productId, final MultipartFile multipartFile) {
        productImageService.updateThumbNailImage(productId, multipartFile);

    }


    @Transactional
    public void deleteProduct(final Long productId) {
        productDeleteService.deleteProduct(productId);
        productImageService.deleteImage(productId);

    }


}
