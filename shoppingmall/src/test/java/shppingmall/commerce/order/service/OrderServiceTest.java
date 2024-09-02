package shppingmall.commerce.order.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import shppingmall.commerce.cart.entity.Cart;
import shppingmall.commerce.cart.repository.CartRepository;
import shppingmall.commerce.order.OrderStatus;
import shppingmall.commerce.order.dto.request.OrderCreateRequestDto;
import shppingmall.commerce.order.dto.request.OrderProductCreateRequestDto;
import shppingmall.commerce.order.dto.request.OrderSearchCondition;
import shppingmall.commerce.order.dto.response.OrderProductCreateResponseDto;
import shppingmall.commerce.order.dto.response.OrderProductResponseDto;
import shppingmall.commerce.order.entity.Order;
import shppingmall.commerce.order.entity.OrderProduct;
import shppingmall.commerce.order.repository.OrderProductRepository;
import shppingmall.commerce.order.repository.OrderRepository;
import shppingmall.commerce.product.entity.Product;
import shppingmall.commerce.product.repository.ProductRepository;
import shppingmall.commerce.support.IntegrationTestSupport;
import shppingmall.commerce.support.TestFixture;
import shppingmall.commerce.user.entity.User;
import shppingmall.commerce.user.entity.UserRole;
import shppingmall.commerce.user.repository.UserRepository;

import java.awt.*;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static shppingmall.commerce.support.TestFixture.*;

class OrderServiceTest extends IntegrationTestSupport {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderProductRepository orderProductRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

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
        Cart cart = Cart.builder().build();

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

    @DisplayName("특정 조건에 맞는 주문상품을 조회할 수 있다.")
    @Test
    void findPagedOrderProductsByUserIdAndStatus() {
        //given
        Product productA = createProduct(10000, "상품A");
        Product productB = createProduct(10000, "상품B");

        Product savedProductA = productRepository.save(productA);
        Product savedProductB = productRepository.save(productB);

        User userA = createUser("userA", "1234", UserRole.BUYER);
        User savedUser = userRepository.save(userA);

        Order newOrder = createOrder(savedUser, OrderStatus.NEW, "test-detailAddress", "test-zipcode");
        Order finishedOrder = createOrder(savedUser, OrderStatus.FINISH, "test-detailAddress", "test-zipcode");

        Order savedOrderNew = orderRepository.save(newOrder);
        Order savedOrderFinished = orderRepository.save(finishedOrder);


        for (int i = 0; i < 50; i++) {
            OrderProduct orderProductA = createOrderProduct(savedOrderNew, savedProductA, i);
            orderProductRepository.save(orderProductA);
        }

        for (int i = 0; i < 50; i++) {
            OrderProduct orderProductB = createOrderProduct(savedOrderFinished, savedProductB, i);
            orderProductRepository.save(orderProductB);
        }

        PageRequest pageRequest = PageRequest.of(0, 10);

        OrderSearchCondition orderSearchCondition = OrderSearchCondition.builder()
                .orderStatus(OrderStatus.FINISH)
                .build();

        //when
        Slice<OrderProductResponseDto> result = orderService.getOrderList(savedUser.getId(), orderSearchCondition, pageRequest);

        //then

        assertThat(result).extracting(OrderProductResponseDto::getProductName, OrderProductResponseDto::getQuantity)
                .containsExactly(
                        tuple("상품B",0),
                        tuple("상품B",1),
                        tuple("상품B",2),
                        tuple("상품B",3),
                        tuple("상품B",4),
                        tuple("상품B",5),
                        tuple("상품B",6),
                        tuple("상품B",7),
                        tuple("상품B",8),
                        tuple("상품B",9)
                );

    }


}