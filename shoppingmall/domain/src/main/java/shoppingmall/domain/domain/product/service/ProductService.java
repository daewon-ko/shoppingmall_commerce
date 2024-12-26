package shoppingmall.domain.domain.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import shoppingmall.domain.domain.product.dto.request.ProductCreateRequestDto;
import shoppingmall.domain.domain.product.dto.response.ProductCreateResponseDto;
import shoppingmall.domain.domain.product.dto.response.ProductUpdateResponseDto;
import shoppingmall.domain.domain.product.repository.ProductQueryRepository;
import shoppingmall.domain.domain.product.repository.ProductRepository;
import shoppingmall.domain.domain.category.entity.Category;
import shoppingmall.domain.domain.category.repository.CategoryRepository;
import shoppingmall.domain.domain.image.dto.response.ImageResponseDto;
import shoppingmall.domain.domain.image.entity.FileType;
import shoppingmall.domain.domain.image.entity.Image;
import shoppingmall.domain.domain.image.repository.ImageRepository;
import shoppingmall.domain.domain.image.service.ImageService;
import shoppingmall.domain.domain.product.dto.request.ProductUpdateRequestDto;
import shoppingmall.domain.domain.product.dto.response.ProductQueryResponseDto;
import shoppingmall.domain.domain.product.entity.Product;
import shoppingmall.domain.domain.product.entity.ProductSearchCondition;
import shoppingmall.domain.domain.user.entity.User;
import shoppingmall.domain.domain.user.service.UserService;
import shoppingmall.common.exception.ApiException;
import shoppingmall.common.exception.domain.CategoryErrorCode;
import shoppingmall.common.exception.domain.ProductErrorCode;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {
    private final ProductRepository productRepository;
    // TODO : Service Layer에서 타 도메인 Repository를 참고하는게 적절한가?
    private final CategoryRepository categoryRepository;
    private final ImageService imageService;
    private final UserService userService;
    private final ImageRepository imageRepository;
    private final ProductQueryRepository productQueryRepository;

    @Transactional
    public ProductCreateResponseDto createProduct(final ProductCreateRequestDto requestDto, List<MultipartFile> images) {
        // 1. requestDTO의 imageURL을 변환 및 저장과정

        Category category = categoryRepository.findById(requestDto.getCategoryId()).orElseThrow(() -> new ApiException(CategoryErrorCode.NO_EXIST_CATEGORY));

        // dto -> product entity 변환 필요
        User seller = userService.findUserByIdAndSeller(requestDto.getSellerId());

        Product product = requestDto.toEntity(category, seller);

        Product savedProduct = productRepository.save(product);

        // 이미지를 파일 타입에 맞춰 저장
        List<Image> thumbnailImages = new ArrayList<>();
        List<Image> detailImages = new ArrayList<>();

        // 첫 번째 이미지는 썸네일로 처리
        if (!images.isEmpty()) {
            MultipartFile thumbnailImage = images.get(0);
            thumbnailImages = imageService.saveImage(List.of(thumbnailImage), savedProduct.getId(), FileType.PRODUCT_THUMBNAIL);

            // 나머지 이미지는 상세 이미지로 처리
            List<MultipartFile> detailImageFiles = images.subList(1, images.size());
            if (!detailImageFiles.isEmpty()) {
                detailImages = imageService.saveImage(detailImageFiles, savedProduct.getId(), FileType.PRODUCT_DETAIL_IMAGE);
            }
        }


        // 저장된 이미지 ID 리스트를 생성
        List<Long> imageIds = new ArrayList<>();
        imageIds.addAll(thumbnailImages.stream().map(Image::getId).toList());
        imageIds.addAll(detailImages.stream().map(Image::getId).toList());

        return ProductCreateResponseDto.of(product, category.getId(), imageIds);

    }


    public Slice<ProductQueryResponseDto> getAllProductList(ProductSearchCondition productSearchCond, Pageable pageable) {
        // 1. 상품조회 -> lazy loading이므로 Category도 함께 조회 필요.
        Slice<Product> products = productQueryRepository.findProductsByCond(productSearchCond, pageable);

        List<ProductQueryResponseDto> productDtos = new ArrayList<>();

        for (Product product : products) {
            List<ImageResponseDto> images = imageService.getImages(product.getId(), productSearchCond.getFileTypes());
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
            imageService.deleteImages(product.getId(), filetypes);
        }

        // 썸네일 이미지가 있으면, 추가한다.
        if (thumbnailImage != null && !thumbnailImage.isEmpty()) {
            List<Image> savedImages = imageService.saveImage(List.of(thumbnailImage), product.getId(), FileType.PRODUCT_THUMBNAIL);
            imageIds = savedImages.stream()
                    .map(i -> i.getId())
                    .collect(toList());
        }

        // 상세이미지가 있으면, 추가한다.
        if (detailImages != null && !detailImages.isEmpty()) {
            List<Image> savedImages = imageService.saveImage(detailImages, product.getId(), FileType.PRODUCT_DETAIL_IMAGE);
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
