package shoppingmall.domainrdb.domain.product.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.mock.web.MockMultipartFile;
import shoppingmall.domainrdb.domain.category.entity.Category;
import shoppingmall.domainrdb.domain.category.repository.CategoryRepository;
import shoppingmall.domainrdb.domain.image.dto.response.ImageResponseDto;
import shoppingmall.domainrdb.domain.image.entity.FileType;
import shoppingmall.domainrdb.domain.image.entity.Image;
import shoppingmall.domainrdb.domain.image.repository.ImageRepository;
import shoppingmall.domainrdb.domain.image.service.ImageService;
import shoppingmall.domainrdb.domain.product.dto.request.ProductCreateRequestDto;
import shoppingmall.domainrdb.domain.product.dto.request.ProductUpdateRequestDto;
import shoppingmall.domainrdb.domain.product.dto.response.ProductCreateResponseDto;
import shoppingmall.domainrdb.domain.product.dto.response.ProductQueryResponseDto;
import shoppingmall.domainrdb.domain.product.dto.response.ProductUpdateResponseDto;
import shoppingmall.domainrdb.domain.product.entity.Product;
import shoppingmall.domainrdb.domain.product.entity.ProductSearchCondition;
import shoppingmall.domainrdb.domain.product.repository.ProductRepository;
import shoppingmall.domainrdb.domain.user.entity.User;
import shoppingmall.domainrdb.domain.user.entity.UserRole;
import shoppingmall.domainrdb.domain.user.repository.UserRepository;
import shoppingmall.domainrdb.support.IntegrationTestSupport;


import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;

class ProductServiceTest extends IntegrationTestSupport {

    @Autowired
    @InjectMocks
    private ProductService productService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ImageRepository imageRepository;

    @MockBean
    private ImageService imageService;


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
        MockMultipartFile thumbNailImage = new MockMultipartFile("thumbNailImage", "test-image.jpg", "image/jpeg", "image.png".getBytes());



        ProductUpdateRequestDto updateProductRequest = ProductUpdateRequestDto.builder()
                .name("변경된 상품A")
                .price(5000)
                .imagesToDelete(imageIds)
                .build();

        //when
        ProductUpdateResponseDto updateProductResponse = productService.updateProduct(createProductResponse.getId(), updateProductRequest,thumbNailImage,  List.of(newUploadImage1, newUploadImage2));

        //then
        assertThat(updateProductResponse)
                .extracting(ProductUpdateResponseDto::getName,
                        ProductUpdateResponseDto::getPrice,
                        ProductUpdateResponseDto::getProductId)
                .containsExactly("변경된 상품A", 5000, createProductResponse.getId());

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
                .price(10000)
                .name("바지")
                .sellerId(user.getId())
                .build();

        ProductCreateResponseDto savedProduct1 = productService.createProduct(request1, List.of(mockMultipartFile1, mockMultipartFile2));

        MockMultipartFile mockMultipartFile3 = new MockMultipartFile("images", "test-image.jpg", "image/jpeg", "image.png".getBytes());
        MockMultipartFile mockMultipartFile4 = new MockMultipartFile("images", "test-image.jpg", "image/jpeg", "image.png".getBytes());

        ProductCreateRequestDto request2 = ProductCreateRequestDto.builder()
                .categoryId(savedCategory.getId())
                .price(5000)
                .name("바지")
                .sellerId(user.getId())
                .build();


        ProductCreateResponseDto savedProduct2 = productService.createProduct(request2, List.of(mockMultipartFile3, mockMultipartFile4));

        ProductSearchCondition searchCond = ProductSearchCondition.builder()
                .categoryId(savedCategory.getId())
                .productName("바지")
                .minPrice(5100)
                .maxPrice(10100)
                .build();


        Pageable pageable = PageRequest.of(0, 1);


        Mockito.when(imageService.getImages(anyLong(), anyList()))
                .thenReturn(List.of(new ImageResponseDto("test-image-url", FileType.PRODUCT_THUMBNAIL, savedProduct1.getId())));

        //when
        Slice<ProductQueryResponseDto> productList = productService.getAllProductList(searchCond, pageable);


        //then
        assertThat(productList).hasSize(1)
                .extracting(ProductQueryResponseDto::getName, ProductQueryResponseDto::getPrice, ProductQueryResponseDto::getCategoryName)
                .containsExactly(
                        tuple(savedProduct1.getName(), savedProduct1.getPrice(), savedProduct1.getCategoryName())
                );

    }


    @DisplayName("상품 생성 후, 검색조건을 충족하지 못하면 조회되지 않는다.")
    @Test

    void notRetrieveWithSomeCondition(){
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
                .name("티셔츠")
                .sellerId(user.getId())
                .build();

        ProductCreateResponseDto savedProduct1 = productService.createProduct(request1, List.of(mockMultipartFile1, mockMultipartFile2));

        MockMultipartFile mockMultipartFile3 = new MockMultipartFile("images", "test-image.jpg", "image/jpeg", "image.png".getBytes());
        MockMultipartFile mockMultipartFile4 = new MockMultipartFile("images", "test-image.jpg", "image/jpeg", "image.png".getBytes());

        ProductCreateRequestDto request2 = ProductCreateRequestDto.builder()
                .categoryId(savedCategory.getId())
                .price(5000)
                .name("바지")
                .sellerId(user.getId())
                .build();


        ProductCreateResponseDto savedProduct2 = productService.createProduct(request2, List.of(mockMultipartFile3, mockMultipartFile4));

        ProductSearchCondition searchCond = ProductSearchCondition.builder()
                .categoryId(savedCategory.getId())
                .productName("바지")
                .minPrice(5100)
                .build();


        Pageable pageable = PageRequest.of(0, 1);


        Mockito.when(imageService.getImages(anyLong(), anyList()))
                .thenReturn(List.of(new ImageResponseDto("test-image-url", FileType.PRODUCT_THUMBNAIL, savedProduct1.getId())));

        //when

        Slice<ProductQueryResponseDto> productList = productService.getAllProductList(searchCond, pageable);

        //then
        assertThat(productList).hasSize(0);
    }

    @DisplayName("상품 리스트를 페이징 처리하여 조회한다.")
    @Test
    void getProductListWithPaging() {
        //given
        User user = createUser("test", "test", UserRole.SELLER);
        user = userRepository.save(user);
        Category category = Category.builder().name("의류").build();
        category = categoryRepository.save(category);

        // 여러 개의 상품 생성
        for (int i = 0; i < 100; i++) {
            ProductCreateRequestDto request = ProductCreateRequestDto.builder()
                    .categoryId(category.getId())
                    .price(1000 * i)
                    .name("상품" + i)
                    .sellerId(user.getId())
                    .build();
            productService.createProduct(request, List.of());
        }

        ProductSearchCondition searchCond = ProductSearchCondition.builder()
                .categoryId(category.getId())
                .build();

                Pageable pageable = PageRequest.of(5, 10, Sort.Direction.DESC, "내림차순");  // 첫 번째 페이지, 페이지 당 5개의 항목

        // Mocking 이미지 서비스
        Mockito.when(imageService.getImages(anyLong(), anyList()))
                .thenReturn(List.of(new ImageResponseDto("test-image-url", FileType.PRODUCT_THUMBNAIL, 1L)));

        //when
        Slice<ProductQueryResponseDto> productList = productService.getAllProductList(searchCond, pageable);

        //then
        assertThat(productList).hasSize(10);
        assertThat(productList.hasNext()).isTrue();  // 다음 페이지가 존재하는지 확인
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
        ProductUpdateResponseDto updateProductResponse = productService.updateProduct(createProductResponse.getId(), updateProductRequest, null, null);
        //then
        assertThat(updateProductResponse.getImages()).isNullOrEmpty();

    }

    @DisplayName("상품을 삭제한다. 상품을 삭제하면 상품 뿐 아니라 연관된 이미지도 삭제된다. ")
    @Test
    void deleteProduct() {

        //given
        Product productA = Product.builder()
                .price(10000)
                .name("상품A")
                .build();

        Product savedProduct = productRepository.save(productA);

        Image image = Image.builder()
                .targetId(savedProduct.getId())
                .fileType(FileType.PRODUCT_THUMBNAIL)
                .build();

        Image savedImage = imageRepository.save(image);

        //when
        productService.deleteProduct(savedProduct.getId());

        //then
        assertThat(productRepository.findById(savedProduct.getId())).isEmpty();
        imageRepository.findById(savedImage.getId()).ifPresent(img -> assertThat(img.getIsDeleted()).isTrue());

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