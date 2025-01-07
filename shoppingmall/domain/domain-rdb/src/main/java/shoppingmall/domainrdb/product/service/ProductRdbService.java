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
    private final CategoryRepository categoryRepository;
    private final ImageRdbService imageRdbService;
    private final UserRdbService userRdbService;
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
    public ProductUpdateResponseDto updateProduct(Long id,
                                                  ProductUpdateRequestDto requestDto,
                                                  MultipartFile thumbnailImage,
                                                  List<MultipartFile> detailImages) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ApiException(ProductErrorCode.PRODUCT_NOT_FOUND));
        Product changedProduct = product.updateDetails(requestDto.getName(), requestDto.getPrice());
        // productRepository를 통하여 Image 객체 조회
        List<Long> imageIds = new ArrayList<>();
        //
        /**
         * MultipartFile이 null이 아니라면, 기존에 존재하던 Image를 삭제한 후, 추가한다?
         * 위와 같은 로직으로 구성한다면, Image를 단순하게 하나를 추가하거나, 하나만 삭제하거나 하는 등과 같은
         * 로직은 따로 Service Layer에서 로직을 별도로 작성해줘야하나?
         *
         */

        // 삭제할 이미지가 존재한다면, 이미지를 삭제한다.
        if (!requestDto.getImagesToDelete().isEmpty()) {
            List<FileType> filetypes = List.of(FileType.PRODUCT_THUMBNAIL, FileType.PRODUCT_DETAIL_IMAGE);
            imageRdbService.deleteImages(product.getId(), filetypes);
        }

        // 썸네일 이미지가 있으면, 추가한다.
        if (thumbnailImage != null && !thumbnailImage.isEmpty()) {
            List<Image> savedImages = imageRdbService.saveImage(List.of(thumbnailImage), product.getId(), FileType.PRODUCT_THUMBNAIL);
            imageIds = savedImages.stream()
                    .map(i -> i.getId())
                    .collect(toList());
        }

        // 상세이미지가 있으면, 추가한다.
        if (detailImages != null && !detailImages.isEmpty()) {
            List<Image> savedImages = imageRdbService.saveImage(detailImages, product.getId(), FileType.PRODUCT_DETAIL_IMAGE);
            imageIds.addAll(savedImages.stream().map(Image::getId).collect(toList()));
        }

        return ProductUpdateResponseDto.builder()
                .productId(changedProduct.getId())
                .name(changedProduct.getName())
                .price(changedProduct.getPrice())
                .images(imageIds)
                .build();

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
