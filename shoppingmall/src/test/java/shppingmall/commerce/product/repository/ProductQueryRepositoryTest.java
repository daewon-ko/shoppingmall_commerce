package shppingmall.commerce.product.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import shppingmall.commerce.category.entity.Category;
import shppingmall.commerce.category.repository.CategoryRepository;
import shppingmall.commerce.chat.repository.MessageRepository;
import shppingmall.commerce.config.JpaConfig;
import shppingmall.commerce.message.repository.ChatRoomRepository;
import shppingmall.commerce.product.ProductSearchCondition;
import shppingmall.commerce.product.entity.Product;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static shppingmall.commerce.support.TestFixture.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)    // DB 교
@Import({JpaConfig.class, ProductQueryRepository.class})
class ProductQueryRepositoryTest {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductQueryRepository productQueryRepository;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private ChatRoomRepository chatRoomRepository;


    @AfterEach
    void tearDown() {
        messageRepository.deleteAllInBatch();
        chatRoomRepository.deleteAllInBatch();
        productRepository.deleteAllInBatch();
        categoryRepository.deleteAllInBatch();

    }


    @DisplayName("상품을 여러 개 저장 후, 특정 가격 이상의 상품만 조회할 수 있다.")
    @Test
    void findProductsByMinPrice() {
        //given

        Category category1 = createCategory("카테고리1");

        categoryRepository.save(category1);

        Product productA = createProduct(category1, 5000, "상품A");
        Product productB = createProduct(category1, 10000, "상품B");
        Product productC = createProduct(category1, 4000, "상품C");
        productRepository.saveAll(List.of(productA, productB, productC));


        ProductSearchCondition searchCond = ProductSearchCondition.builder()
                .categoryId(category1.getId())
                .minPrice(5100)
                .build();

        PageRequest pageRequest = PageRequest.of(0, 10);

        //when
        Slice<Product> products = productQueryRepository.findProductsByCond(searchCond, pageRequest);

        //then
        assertThat(products.get()).extracting(Product::getName).containsExactly("상품B");

    }

    @DisplayName("상품을 저장 후 특정 가격 이하의 상품만 조회할 수 있다.")
    @Test
    void findProductByMaxPrice() {
        //given

        Category category1 = createCategory("카테고리1");

        categoryRepository.save(category1);

        Product productA = createProduct(category1, 5000, "상품A");
        Product productB = createProduct(category1, 10000, "상품B");
        Product productC = createProduct(category1, 4000, "상품C");
        productRepository.saveAll(List.of(productA, productB, productC));

        ProductSearchCondition searchCond = ProductSearchCondition.builder()
                .maxPrice(10001)
                .categoryId(category1.getId())
                .build();

        Pageable pageRequest = PageRequest.of(0, 10);

        //when
        Slice<Product> products = productQueryRepository.findProductsByCond(searchCond, pageRequest);

        //then
        assertThat(products.get()).extracting(Product::getName).contains("상품A", "상품B", "상품C");


    }

    @DisplayName("특정 시점 이후에 등록된 모든 상품을 조회할 수 있다.")
    @Test
    void findProductsBeforeNow() {
        //given

        Category category1 = createCategory("카테고리1");

        categoryRepository.save(category1);

        Product productA = createProduct(category1, 5000, "상품A");
        Product productB = createProduct(category1, 10000, "상품B");
        Product productC = createProduct(category1, 4000, "상품C");
        productRepository.saveAll(List.of(productA, productB, productC));


        ProductSearchCondition searchCond = ProductSearchCondition.builder()
                .categoryId(category1.getId())
                .startDate(LocalDateTime.of(2024, 10, 16, 13, 00))
                .build();

        Pageable pageRequest = PageRequest.of(0, 10);
        //when
        Slice<Product> products = productQueryRepository.findProductsByCond(searchCond, pageRequest);
        //then
        assertThat(products.getContent()).extracting(Product::getName)
                .contains("상품A", "상품B", "상품C");

    }

}