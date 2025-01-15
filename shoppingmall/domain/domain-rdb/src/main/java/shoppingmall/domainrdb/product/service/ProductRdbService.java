package shoppingmall.domainrdb.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.transaction.annotation.Transactional;
import shoppingmall.common.exception.ApiException;
import shoppingmall.common.exception.domain.ProductErrorCode;
import shoppingmall.domainrdb.common.annotation.DomainService;
import shoppingmall.domainrdb.image.entity.FileType;
import shoppingmall.domainrdb.image.entity.Image;
import shoppingmall.domainrdb.image.repository.ImageRepository;
import shoppingmall.domainrdb.mapper.ProductEntityMapper;
import shoppingmall.domainrdb.product.domain.ProductDomain;
import shoppingmall.domainrdb.product.dto.request.ProductSearchCondition;
import shoppingmall.domainrdb.product.entity.Product;
import shoppingmall.domainrdb.product.repository.ProductQueryRepository;
import shoppingmall.domainrdb.product.repository.ProductRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


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

        // 아래 객체를 ProductDomain으로 변환해서 Return 필요

        Slice<Product> productsByCond = productQueryRepository.findProductsByCond(productSearchCond, pageable);

        // Product Entity -> ProductDomain 변환
        List<ProductDomain> productDomainList = productsByCond.stream()
                .map(product -> {
                    ProductDomain productDomain = ProductEntityMapper.toProductDomain(product);
                    return productDomain;
                }).collect(Collectors.toUnmodifiableList());

        return new SliceImpl<>(productDomainList, pageable, productsByCond.hasNext());


    }

    @Transactional
    public void updateProduct(final ProductDomain productDomain) {

        Product product = productRepository.findById(productDomain.getProductId().getValue()).orElseThrow(() -> new ApiException(ProductErrorCode.PRODUCT_NOT_FOUND));
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

    public Boolean existByProductId(final Long productId) {
        return productRepository.existsById(productId);
    }


    public ProductDomain getProductDomainByProductId(final Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ApiException(ProductErrorCode.PRODUCT_NOT_FOUND));
        return ProductEntityMapper.toProductDomain(product);
    }
}
