package shoppingmall.domainrdb.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.transaction.annotation.Transactional;
import shoppingmall.common.exception.ApiException;
import shoppingmall.common.exception.domain.ProductErrorCode;
import shoppingmall.domainrdb.category.repository.CategoryRepository;
import shoppingmall.domainrdb.common.annotation.DomainService;
import shoppingmall.domainrdb.image.entity.Image;
import shoppingmall.domainrdb.image.repository.ImageRepository;
import shoppingmall.domainrdb.image.service.ImageRdbService;
import shoppingmall.domainrdb.mapper.ProductEntityMapper;
import shoppingmall.domainrdb.product.ProductDomain;
import shoppingmall.domainrdb.product.dto.request.ProductSearchCondition;
import shoppingmall.domainrdb.product.entity.Product;
import shoppingmall.domainrdb.product.repository.ProductQueryRepository;
import shoppingmall.domainrdb.product.repository.ProductRepository;
import shoppingmall.domainrdb.user.service.UserRdbService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@DomainService
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductRdbService {
    private final ProductRepository productRepository;
    // TODO : Service Layer에서 타 도메인 Repository를 참고하는게 적절한가?

    private final ImageRepository imageRepository;
    private final ProductQueryRepository productQueryRepository;


    @Transactional
    public Long createProduct(final ProductDomain productDomain) {

        Product savedProduct = productRepository.save(ProductEntityMapper.createProductEntity(productDomain));
        return savedProduct.getId();

    }


    public Slice<ProductDomain> getAllProductList(final ProductSearchCondition productSearchCond, Pageable pageable) {
        // 1. 상품조회 -> lazy loading이므로 Category도 함께 조회 필요.

        // 아래 객체를 ProductDomain으로 변환해서 Return 필요

        Slice<Product> productsByCond = productQueryRepository.findProductsByCond(productSearchCond, pageable);

        // Product Entity -> ProductDomain 변환
        List<ProductDomain> productDomainList = productsByCond.stream()
                .map(product -> {
                    ProductDomain productDomain = ProductEntityMapper.toProductDomain(product);
                    return productDomain;
                }).collect(Collectors.toUnmodifiableList());

        return new SliceImpl<>(productDomainList, pageable, productsByCond.hasNext());


//        List<ProductQueryResponseDto> productDtos = new ArrayList<>();

//        for (Product product : products) {
        // ImageService에서 정의 필요
//            List<ImageResponseDto> images = imageRdbService.getImage(product.getId(), productSearchCond.getFileTypes());
//            ProductQueryResponseDto productQueryResponseDto = ProductQueryResponseDto.of(product, images);
//            productDtos.add(productQueryResponseDto);
    }

//        return new SliceImpl<>(productDtos, pageable, products.hasNext());


    // 2. 상품과 연관된 이미지 조회
    // targetId와 저장된 Image 중 첫번째로 저장된 이미지가 thumbNailImage


//        return productQueryRepository.findAllByProductId(productSearchCond, pageable);

    @Transactional
    public void updateProduct(final ProductDomain productDomain) {

        Product product = productRepository.findById(productDomain.getId()).orElseThrow(() -> new ApiException(ProductErrorCode.PRODUCT_NOT_FOUND));
        product.updateDetails(productDomain.getName(), productDomain.getPrice());

    }


    @Transactional
    public void deleteProduct(Long id) {
        // 상품 삭제
        productRepository.deleteById(id);
        List<Image> images = imageRepository.findImagesByTargetIdAndFileType(List.of(FileType.PRODUCT_THUMBNAIL, FileType.PRODUCT_DETAIL_IMAGE), id);

        // Soft-Delete
        for (Image image : images) {
            image.deleteImage(LocalDateTime.now());
        }
    }
}
