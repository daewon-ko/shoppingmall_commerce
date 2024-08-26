package shppingmall.commerce.product.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import shppingmall.commerce.category.entity.Category;
import shppingmall.commerce.category.repository.CategoryRepository;
import shppingmall.commerce.image.entity.FileType;
import shppingmall.commerce.image.repository.ImageRepository;
import shppingmall.commerce.product.dto.request.ProductCreateRequestDto;
import shppingmall.commerce.product.dto.request.ProductUpdateRequestDto;
import shppingmall.commerce.product.dto.response.ProductCreateResponseDto;
import shppingmall.commerce.product.dto.response.ProductUpdateResponseDto;
import shppingmall.commerce.product.entity.Product;
import shppingmall.commerce.product.repository.ProductRepository;
import shppingmall.commerce.support.IntegrationTestSupport;
import shppingmall.commerce.user.entity.User;
import shppingmall.commerce.user.entity.UserRole;
import shppingmall.commerce.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class ProductServiceTest extends IntegrationTestSupport {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ImageRepository imageRepository;


    @AfterEach
    void tearDown() {
        productRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();
        categoryRepository.deleteAllInBatch();

    }


    //TODO : Image생성을 여기서 검증해야할까? Mock으로 하는게 더 자연스럽지 않을까?
    // Image 생성관련해서는 ImageService에서 검증하는게 더 자연스럽지 않을까?
    @DisplayName("이미지를 1개 추가한 상품을 생성하면 상품이 한 개 생성된다.")
    @Test
    void createProductWithOnlyOneImage() {
        //given
        User user = createUser("test", "test", UserRole.SELLER);

        user = userRepository.save(user);

        Category category1 = Category.builder()
                .name("의류").build();
        Category savedCategory = categoryRepository.save(category1);

        ProductCreateRequestDto request = ProductCreateRequestDto.builder()
                .categoryId(savedCategory.getId())
                .price(5000)
                .name("티셔츠")
                .sellerId(user.getId())
                .build();

        MockMultipartFile mockMultipartFile = new MockMultipartFile("images", "test-image.jpg", "image/jpeg", "image.png".getBytes());


        //when
        productService.createProduct(request, List.of(mockMultipartFile));

        //then
        List<Product> products = productRepository.findAll();
        assertThat(products).hasSize(1)
                .extracting(p -> p.getCategory().getId(), Product::getName, Product::getPrice)
                .contains(tuple(savedCategory.getId(), "티셔츠", 5000)
                );

    }


    @DisplayName("이미지 2개를 추가한 상품을 생성해도 상품은 한 개가 생성된다.")
    @Test
    void createProductWithTwoImages() {
        //given

        User user = createUser("test", "test", UserRole.SELLER);
        user = userRepository.save(user);

        Category category1 = Category.builder()
                .name("의류").build();


        Category savedCategory = categoryRepository.save(category1);


        MockMultipartFile mockMultipartFile1 = new MockMultipartFile("images", "test-image.jpg", "image/jpeg", "image.png".getBytes());
        MockMultipartFile mockMultipartFile2 = new MockMultipartFile("images", "test-image.jpg", "image/jpeg", "image.png".getBytes());

        ProductCreateRequestDto request = ProductCreateRequestDto.builder()
                .categoryId(savedCategory.getId())
                .price(5000)
                .name("바지")
                .sellerId(user.getId())
                .build();


        //when
        productService.createProduct(request, List.of(mockMultipartFile1, mockMultipartFile2));

        //then
        List<Product> products = productRepository.findAll();
        assertThat(products).hasSize(1)
                .extracting(p -> p.getCategory().getId(), Product::getName, Product::getPrice)
                .contains(
                        tuple(savedCategory.getId(), "바지", 5000)
                );
    }

    @DisplayName("상품을 생성한 후 전체 상품 개수를 조회한다.")
    @Test
    void createProductsThenGetProductList() {
        //given
        User user = createUser("test", "test", UserRole.SELLER);
        user = userRepository.save(user);
        Category category1 = Category.builder()
                .name("의류").build();
        Category savedCategory = categoryRepository.save(category1);


        MockMultipartFile mockMultipartFile1 = new MockMultipartFile("images", "test-image.jpg", "image/jpeg", "image.png".getBytes());
        MockMultipartFile mockMultipartFile2 = new MockMultipartFile("images", "test-image.jpg", "image/jpeg", "image.png".getBytes());

        ProductCreateRequestDto request1 = ProductCreateRequestDto.builder()
                .categoryId(savedCategory.getId())
                .price(5000)
                .name("바지")
                .sellerId(user.getId())
                .build();

        productService.createProduct(request1, List.of(mockMultipartFile1, mockMultipartFile2));

        MockMultipartFile mockMultipartFile3 = new MockMultipartFile("images", "test-image.jpg", "image/jpeg", "image.png".getBytes());
        MockMultipartFile mockMultipartFile4 = new MockMultipartFile("images", "test-image.jpg", "image/jpeg", "image.png".getBytes());

        ProductCreateRequestDto request2 = ProductCreateRequestDto.builder()
                .categoryId(savedCategory.getId())
                .price(5000)
                .name("바지")
                .sellerId(user.getId())
                .build();

        productService.createProduct(request2, List.of(mockMultipartFile3, mockMultipartFile4));


        //when
        List<ProductCreateResponseDto> allProductList = productService.getAllProductList();
        //then
        assertThat(allProductList).hasSize(2)
                .extracting(ProductCreateResponseDto::getCategoryId, ProductCreateResponseDto::getName, ProductCreateResponseDto::getPrice)
                .contains(
                        tuple(savedCategory.getId(), request1.getName(), request1.getPrice()),
                        tuple(savedCategory.getId(), request2.getName(), request2.getPrice()));

    }

    @DisplayName("상품을 저장한 후 수정한다.")
    @Test
    void updateProduct() {
        //given
        User user = createUser("test", "test", UserRole.SELLER);
        user = userRepository.save(user);
        Category category1 = Category.builder()
                .name("의류").build();
        Category savedCategory = categoryRepository.save(category1);


        MockMultipartFile mockMultipartFile1 = new MockMultipartFile("images", "test-image.jpg", "image/jpeg", "image.png".getBytes());
        MockMultipartFile mockMultipartFile2 = new MockMultipartFile("images", "test-image.jpg", "image/jpeg", "image.png".getBytes());

        ProductCreateRequestDto createProductRequest = ProductCreateRequestDto.builder()
                .categoryId(savedCategory.getId())
                .price(5000)
                .name("바지")
                .sellerId(user.getId())
                .build();

        ProductCreateResponseDto createProductResponse = productService.createProduct(createProductRequest, List.of(mockMultipartFile1, mockMultipartFile2));
        List<Long> imageIds = createProductResponse.getImageIds();

        MockMultipartFile newUploadImage1 = new MockMultipartFile("newUploadImage1", "test-image.jpg", "image/jpeg", "image.png".getBytes());
        MockMultipartFile newUploadImage2 = new MockMultipartFile("newUploadImage2", "test-image.jpg", "image/jpeg", "image.png".getBytes());


        ProductUpdateRequestDto updateProductRequest = ProductUpdateRequestDto.builder()
                .name("변경된 상품A")
                .price(5000)
                .imagesToDelete(imageIds)
                .build();

        //when
        ProductUpdateResponseDto updateProductResponse = productService.updateProduct(createProductResponse.getId(), updateProductRequest, List.of(newUploadImage1, newUploadImage2));

        //then
        assertThat(updateProductResponse)
                .extracting(ProductUpdateResponseDto::getName,
                        ProductUpdateResponseDto::getPrice,
                        ProductUpdateResponseDto::getProductId)
                .containsExactly("변경된 상품A", 5000, createProductResponse.getId());

    }

    @DisplayName("이미지를 삭제하는 상품 수정이 요청되면, 이미지를 삭제할 수 있다. ")
    @Test
    void updateProductWithOutAddingImages() {
        //given
        User user = createUser("test", "test", UserRole.SELLER);
        user = userRepository.save(user);
        Category category1 = Category.builder()
                .name("의류").build();
        Category savedCategory = categoryRepository.save(category1);


        MockMultipartFile mockMultipartFile1 = new MockMultipartFile("images", "test-image.jpg", "image/jpeg", "image.png".getBytes());
        MockMultipartFile mockMultipartFile2 = new MockMultipartFile("images", "test-image.jpg", "image/jpeg", "image.png".getBytes());

        ProductCreateRequestDto createProductRequest = ProductCreateRequestDto.builder()
                .categoryId(savedCategory.getId())
                .price(5000)
                .name("바지")
                .sellerId(user.getId())
                .build();

        ProductCreateResponseDto createProductResponse = productService.createProduct(createProductRequest, List.of(mockMultipartFile1, mockMultipartFile2));
        List<Long> imageIds = createProductResponse.getImageIds();


        ProductUpdateRequestDto updateProductRequest = ProductUpdateRequestDto.builder()
                .name("변경된 상품A")
                .price(5000)
                .imagesToDelete(imageIds)
                .build();


        //when
        ProductUpdateResponseDto updateProductResponse = productService.updateProduct(createProductResponse.getId(), updateProductRequest, null);
        //then
        assertThat(updateProductResponse.getImages()).isNullOrEmpty();

    }


    private static User createUser(String name, String password, UserRole userRole) {
        User user = User.builder()
                .name(name)
                .password(password)
                .userRole(userRole)
                .build();
        return user;
    }


    private Product createProduct(Category category, String name, int price, LocalDateTime localDateTime) {
        return Product.builder()
                .category(category)
                .name(name)
                .price(price)
                .build();
    }


}