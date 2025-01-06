package shoppingmall.web.api.product.usecase;


import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import shoppingmall.domainrdb.product.ProductDomain;
import shoppingmall.domainservice.domain.product.service.ProductCreateService;
import shoppingmall.domainservice.domain.product.service.ProductImageService;
import shoppingmall.web.api.product.dto.request.ProductCreateRequestDto;
import shoppingmall.web.api.product.dto.response.ProductCreateResponseDto;
import shoppingmall.web.common.mapper.ProductMapper;
import shoppingmall.web.common.annotataion.Usecase;

import java.util.List;

@Usecase
@RequiredArgsConstructor
public class ProductUsecase {
    private final ProductCreateService productCreateService;
    private final ProductImageService productImageService;
//
//    private final ProductSearchService productSearchService;
//    private final ProductUpdateService productUpdateService;
//    private final ProductDeleteService productDeleteService;



    @Transactional
    public ProductCreateResponseDto createProduct(final ProductCreateRequestDto productCreateRequestDto, final List<MultipartFile> multipartFiles) {

        ProductDomain productDomain = ProductMapper.toProduct(productCreateRequestDto);


        Long productId = productCreateService.createProduct(productDomain);

        final List<Long> imageIds = productImageService.saveProductImages(multipartFiles, productId);

        return ProductMapper.toResponseDto(productDomain, productId, imageIds);

    }




}
