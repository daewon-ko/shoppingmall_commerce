package shppingmall.commerce.product.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import shppingmall.commerce.category.entity.Category;
import shppingmall.commerce.category.repository.CategoryRepository;
import shppingmall.commerce.product.dto.request.ProductRequestDto;
import shppingmall.commerce.product.dto.response.ProductResponseDto;
import shppingmall.commerce.product.entity.Product;
import shppingmall.commerce.product.repository.ProductRepository;
import shppingmall.commerce.support.IntegrationTest;
import shppingmall.commerce.user.entity.User;
import shppingmall.commerce.user.entity.UserRole;
import shppingmall.commerce.user.repository.UserRepository;
import shppingmall.commerce.user.service.UserService;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class ProductServiceTest extends IntegrationTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;


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

        ProductRequestDto request = ProductRequestDto.builder()
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

        ProductRequestDto request = ProductRequestDto.builder()
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

        ProductRequestDto request1 = ProductRequestDto.builder()
                .categoryId(savedCategory.getId())
                .price(5000)
                .name("바지")
                .sellerId(user.getId())
                .build();

        productService.createProduct(request1, List.of(mockMultipartFile1, mockMultipartFile2));

        MockMultipartFile mockMultipartFile3 = new MockMultipartFile("images", "test-image.jpg", "image/jpeg", "image.png".getBytes());
        MockMultipartFile mockMultipartFile4 = new MockMultipartFile("images", "test-image.jpg", "image/jpeg", "image.png".getBytes());

        ProductRequestDto request2 = ProductRequestDto.builder()
                .categoryId(savedCategory.getId())
                .price(5000)
                .name("바지")
                .sellerId(user.getId())
                .build();

        productService.createProduct(request2, List.of(mockMultipartFile3, mockMultipartFile4));


        //when
        List<ProductResponseDto> allProductList = productService.getAllProductList();
        //then
        assertThat(allProductList).hasSize(2)
                .extracting(ProductResponseDto::getCategoryId, ProductResponseDto::getName, ProductResponseDto::getPrice)
                .contains(
                        tuple(savedCategory.getId(), request1.getName(), request1.getPrice()),
                        tuple(savedCategory.getId(), request2.getName(), request2.getPrice()));

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