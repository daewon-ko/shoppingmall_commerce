package shppingmall.commerce.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import shppingmall.commerce.category.entity.Category;
import shppingmall.commerce.category.repository.CategoryRepository;
import shppingmall.commerce.image.entity.FileType;
import shppingmall.commerce.image.entity.Image;
import shppingmall.commerce.image.service.ImageService;
import shppingmall.commerce.product.dto.request.ProductUpdateRequestDto;
import shppingmall.commerce.product.dto.request.ProductCreateRequestDto;
import shppingmall.commerce.product.dto.response.ProductCreateResponseDto;
import shppingmall.commerce.product.dto.response.ProductUpdateResponseDto;
import shppingmall.commerce.product.entity.Product;
import shppingmall.commerce.product.repository.ProductRepository;
import shppingmall.commerce.user.entity.User;
import shppingmall.commerce.user.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {
    private final ProductRepository productRepository;
    // TODO : Service Layer에서 타 도메인 Repository를 참고하는게 적절한가?
    private final CategoryRepository categoryRepository;
    private final ImageService imageService;
    private final UserService userService;

    @Transactional
    public ProductCreateResponseDto createProduct(final ProductCreateRequestDto requestDto, List<MultipartFile> images) {
        // 1. requestDTO의 imageURL을 변환 및 저장과정


        // TODO : 예외정의(?) 필요
        Category category = categoryRepository.findById(requestDto.getCategoryId()).orElseThrow(() -> new IllegalArgumentException("해당하는 카테고리가 없습니다."));

        // dto -> product entity 변환 필요
        User seller = userService.findUserByIdAndSeller(requestDto.getSellerId());

        Product product = requestDto.toEntity(category, seller);

        Product savedProduct = productRepository.save(product);


        List<Image> imageList = imageService.saveImage(images, savedProduct.getId(), FileType.PRODUCT_IMAGE);

        List<Long> imageIds = imageList.stream()
                .map(image -> image.getId())
                .collect(Collectors.toList());

        return ProductCreateResponseDto.of(product, category.getId(), imageIds);

    }


    public List<ProductCreateResponseDto> getAllProductList() {
        List<ProductCreateResponseDto> list = new ArrayList<>();
        List<Product> productList = productRepository.findAll();

        for (Product product : productList) {
            list.add(product.toDto());
        }
        return list;
    }

    @Transactional
    public ProductUpdateResponseDto updateProduct(Long id, ProductUpdateRequestDto requestDto) {
        Product product = productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당하는 상품이 존재하지 않습니다."));
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
            imageService.deleteImages(product.getId(), FileType.PRODUCT_IMAGE);
        }

        // 이미지를 추가한다.(저장한다.)
        if (requestDto.getImages() != null) {
            List<Image> images = imageService.saveImage(requestDto.getImages(), product.getId(), FileType.PRODUCT_IMAGE);
             imageIds = images.stream()
                    .map(i -> i.getId())
                    .collect(Collectors.toList());

        }

        return ProductUpdateResponseDto.builder()
                .productId(changedProduct.getId())
                .name(changedProduct.getName())
                .price(changedProduct.getPrice())
                .images(imageIds)
                .build();


    }
}
