package shoppingmall.domainrdb.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.transaction.annotation.Transactional;
import shoppingmall.domainrdb.category.CategoryDomain;
import shoppingmall.domainrdb.category.entity.Category;
import shoppingmall.domainrdb.category.repository.CategoryRepository;
import shoppingmall.domainrdb.common.annotation.DomainService;
import shoppingmall.domainrdb.image.entity.Image;
import shoppingmall.domainrdb.image.repository.ImageRepository;
import shoppingmall.domainrdb.image.service.ImageRdbService;
import shoppingmall.domainrdb.mapper.ProductEntityMapper;
import shoppingmall.domainrdb.product.ProductDomain;
import shoppingmall.domainrdb.product.repository.ProductQueryRepository;
import shoppingmall.domainrdb.product.repository.ProductRepository;
import shoppingmall.domainrdb.product.entity.Product;
import shoppingmall.common.exception.ApiException;
import shoppingmall.common.exception.domain.CategoryErrorCode;
import shoppingmall.common.exception.domain.ProductErrorCode;
import shoppingmall.domainrdb.user.UserDomain;
import shoppingmall.domainrdb.user.entity.User;
import shoppingmall.domainrdb.user.service.UserRdbService;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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


    public Slice<ProductQueryResponseDto> getAllProductList(ProductSearchCondition productSearchCond, Pageable pageable) {
        // 1. 상품조회 -> lazy loading이므로 Category도 함께 조회 필요.
        Slice<Product> products = productQueryRepository.findProductsByCond(productSearchCond, pageable);

        List<ProductQueryResponseDto> productDtos = new ArrayList<>();

        for (Product product : products) {
            List<ImageResponseDto> images = imageRdbService.getImages(product.getId(), productSearchCond.getFileTypes());
            ProductQueryResponseDto productQueryResponseDto = ProductQueryResponseDto.of(product, images);
            productDtos.add(productQueryResponseDto);
        }

        return new SliceImpl<>(productDtos, pageable, products.hasNext());



        // 2. 상품과 연관된 이미지 조회
        // targetId와 저장된 Image 중 첫번째로 저장된 이미지가 thumbNailImage



//        return productQueryRepository.findAllByProductId(productSearchCond, pageable);


    }

    // 상품 전체 이미지 조회 기능의 메서드


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

    //TODO : 상품을 삭제할때, 상품과 연관된 Image들도 삭제해주는게 맞을까?
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
