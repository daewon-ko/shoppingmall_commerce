package shoppingmall.web.api.product.usecase;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import shoppingmall.domainrdb.image.ImageDomain;
import shoppingmall.domainrdb.product.ProductDomain;
import shoppingmall.domainservice.domain.product.service.*;
import shoppingmall.web.api.product.dto.request.ProductCreateRequestDto;
import shoppingmall.web.api.product.dto.request.ProductSearchConditionRequestDto;
import shoppingmall.web.api.product.dto.request.ProductUpdateRequestDto;
import shoppingmall.web.api.product.dto.response.ProductCreateResponseDto;
import shoppingmall.web.api.product.dto.response.ProductQueryResponseDto;
import shoppingmall.web.common.mapper.ImageDtoMapper;
import shoppingmall.web.common.mapper.ProductDtoMapper;
import shoppingmall.web.common.annotataion.Usecase;

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


    @Transactional
    public ProductCreateResponseDto createProduct(final ProductCreateRequestDto productCreateRequestDto, final List<MultipartFile> multipartFiles) {

        // ProductDomain 생성시 Validation 수행
        // CategoryDomain, UserDomain도 인스턴스 생성 시 생성자를 통해 Validation 수행
        ProductDomain productDomain = ProductDtoMapper.toProductDomain(productCreateRequestDto);


        Long productId = productCreateService.createProduct(productDomain);

        final List<Long> imageIds = productImageService.saveProductImages(multipartFiles, productId);

        return ProductDtoMapper.toCreateResponseDto(productDomain, productId, imageIds);

    }

    public Slice<ProductQueryResponseDto> getAllProductList(final ProductSearchConditionRequestDto searchConditionRequestDto, final Pageable pageable) {


        Slice<ProductDomain> productDomains = productSearchService.searchProducts(ProductDtoMapper.toSearchCondition(searchConditionRequestDto), pageable);

        // ProductDomain -> ProductResponseDTO

        List<ProductQueryResponseDto> listDtos = new ArrayList<>();
        for (ProductDomain productDomain : productDomains) {

            List<ImageDomain> imageDomains = productImageService.searchProductImages(productDomain.getId(), searchConditionRequestDto.getFileTypes());

            listDtos.add(ProductDtoMapper.toSearchResponseDto(productDomain, ImageDtoMapper.toImageResponseDto(imageDomains), searchConditionRequestDto.getCategoryId()));


        }
        return new SliceImpl<>(listDtos, pageable, productDomains.hasNext());


    }


    public void updateProducts(final Long productId, final ProductUpdateRequestDto updateRequestDto) {
        productUpdateService.updateProduct(ProductDtoMapper.toProductDomain(productId, updateRequestDto));
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
