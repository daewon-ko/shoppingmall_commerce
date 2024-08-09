package shppingmall.commerce.order.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import shppingmall.commerce.cart.entity.Cart;
import shppingmall.commerce.cart.repository.CartRepository;
import shppingmall.commerce.category.repository.CategoryRepository;
import shppingmall.commerce.order.dto.request.OrderCreateRequestDto;
import shppingmall.commerce.order.dto.request.OrderProductCreateRequestDto;
import shppingmall.commerce.order.dto.response.OrderProductCreateResponseDto;
import shppingmall.commerce.order.repository.OrderProductRepository;
import shppingmall.commerce.order.repository.OrderRepository;
import shppingmall.commerce.product.entity.Product;
import shppingmall.commerce.product.repository.ProductRepository;
import shppingmall.commerce.support.IntegrationTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class OrderServiceTest extends IntegrationTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderProductRepository orderProductRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CartRepository cartRepository;

    @AfterEach
    void tearDown() {
        orderProductRepository.deleteAllInBatch();
        orderRepository.deleteAllInBatch();
        productRepository.deleteAllInBatch();
        cartRepository.deleteAllInBatch();

    }

    @DisplayName("장바구니 생성 없이 하나의 상품을 바로 주문을 한다.")
    @Test
    void createDirectOrderProduct() {
        //given
        Product product = createProduct(10000, "상품A");

        Product savedProduct = productRepository.save(product);

        OrderProductCreateRequestDto orderProductCreateRequestDto = OrderProductCreateRequestDto.builder()
                .productId(savedProduct.getId())
                .quantity(99)
                .build();

        List<OrderProductCreateRequestDto> dtoList = List.of(orderProductCreateRequestDto);

        OrderCreateRequestDto orderCreateRequestDto = OrderCreateRequestDto.builder()
                .zipCode("test-zipcode")
                .detailAddress("test-detail")
                .orderProductRequestDtoList(dtoList)
                .build();


        //when
        List<OrderProductCreateResponseDto> directOrder = orderService.createDirectOrder(orderCreateRequestDto);

        //then
        assertThat(directOrder.size()).isEqualTo(1);
        assertThat(directOrder.get(0).getQuantity()).isEqualTo(99);

    }

    @DisplayName("장바구니 생성 없이 여러 개의 상품을 주문한다. ")
    @Test
    void createDirectWithOrderProducts() {
        //given
        Product product1 = createProduct(10000, "상품A");
        Product product2 = createProduct(10000, "상품B");

        Product savedProduct1 = productRepository.save(product1);
        Product savedProduct2 = productRepository.save(product2);

        OrderProductCreateRequestDto orderProductCreateRequestDto1 = OrderProductCreateRequestDto.builder()
                .productId(savedProduct1.getId())
                .quantity(99)
                .build();

        OrderProductCreateRequestDto orderProductCreateRequestDto2 = OrderProductCreateRequestDto.builder()
                .productId(savedProduct2.getId())
                .quantity(100)
                .build();

        List<OrderProductCreateRequestDto> orderProductRequestList = List.of(orderProductCreateRequestDto1, orderProductCreateRequestDto2);

        OrderCreateRequestDto orderCreateRequest = OrderCreateRequestDto.builder()
                .zipCode("test-zipcode")
                .detailAddress("test-detail")
                .orderProductRequestDtoList(orderProductRequestList)
                .build();


        //when
        List<OrderProductCreateResponseDto> orderProductResponseList = orderService.createDirectOrder(orderCreateRequest);

        //then
        assertThat(orderProductResponseList).hasSize(2)
                .extracting(OrderProductCreateResponseDto::getQuantity)
                .containsExactly(99, 100);

    }


    @DisplayName("장바구니를 통해 여러 상품을 주문을 한다.")
    @Test
    void createOrderCart() {
        //given
        Cart cart = new Cart(1L);

        Cart savedCart = cartRepository.save(cart);

        Product product1 = createProduct(10000, "상품A");
        Product product2 = createProduct(10000, "상품B");

        Product savedProduct1 = productRepository.save(product1);
        Product savedProduct2 = productRepository.save(product2);


        OrderProductCreateRequestDto orderProductCreateRequestDto1 = OrderProductCreateRequestDto.builder()
                .productId(savedProduct1.getId())
                .quantity(99)
                .build();

        OrderProductCreateRequestDto orderProductCreateRequestDto2 = OrderProductCreateRequestDto.builder()
                .productId(savedProduct2.getId())
                .quantity(100)
                .build();

        List<OrderProductCreateRequestDto> orderProductRequestList = List.of(orderProductCreateRequestDto1, orderProductCreateRequestDto2);


        OrderCreateRequestDto orderCreateRequest = OrderCreateRequestDto.builder()
                .cartId(savedCart.getId())
                .zipCode("test-zipcode")
                .detailAddress("test-detail")
                .orderProductRequestDtoList(orderProductRequestList)
                .build();

        //when

        List<OrderProductCreateResponseDto> responseDtoList = orderService.createDirectOrder(orderCreateRequest);

        //then
        assertThat(responseDtoList).hasSize(2)
                .extracting(OrderProductCreateResponseDto::getQuantity)
                .containsExactly(99, 100);

    }


    private static Product createProduct(int price, String name) {
        Product product = Product.builder()
                .price(price)
                .name(name)
                .build();
        return product;
    }


}