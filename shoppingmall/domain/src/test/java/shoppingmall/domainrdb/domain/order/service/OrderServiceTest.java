package shoppingmall.domainrdb.domain.order.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import shoppingmall.domainrdb.domain.cart.entity.Cart;
import shoppingmall.domainrdb.domain.cart.repository.CartRepository;
import shoppingmall.domainrdb.domain.order.dto.request.*;
import shoppingmall.domainrdb.domain.order.dto.response.OrderProductCreateResponseDto;
import shoppingmall.domainrdb.domain.order.dto.response.OrderProductResponseDto;
import shoppingmall.domainrdb.domain.order.entity.Order;
import shoppingmall.domainrdb.domain.order.entity.OrderProduct;
import shoppingmall.domainrdb.domain.order.entity.OrderStatus;
import shoppingmall.domainrdb.domain.order.repository.OrderProductRepository;
import shoppingmall.domainrdb.domain.order.repository.OrderRepository;
import shoppingmall.domainrdb.domain.product.entity.Product;
import shoppingmall.domainrdb.domain.product.repository.ProductRepository;
import shoppingmall.domainrdb.domain.user.entity.User;
import shoppingmall.domainrdb.domain.user.entity.UserRole;
import shoppingmall.domainrdb.domain.user.repository.UserRepository;
import shoppingmall.common.exception.ApiException;
import shoppingmall.domainrdb.support.IntegrationTestSupport;


import java.util.List;

import static org.assertj.core.api.Assertions.*;

import static shoppingmall.domainrdb.support.TestFixture.*;
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
        Order finishedOrder = createOrder(savedUser, OrderStatus.ORDER_FINISH, "test-detailAddress", "test-zipcode");

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
                .orderStatus(OrderStatus.ORDER_FINISH)
                .build();

        //when
        Slice<OrderProductResponseDto> result = orderService.getOrderList(savedUser.getId(), orderSearchCondition, pageRequest);

        //then

        assertThat(result).extracting(OrderProductResponseDto::getProductName, OrderProductResponseDto::getQuantity)
                .containsExactly(
                        tuple("상품B", 0),
                        tuple("상품B", 1),
                        tuple("상품B", 2),
                        tuple("상품B", 3),
                        tuple("상품B", 4),
                        tuple("상품B", 5),
                        tuple("상품B", 6),
                        tuple("상품B", 7),
                        tuple("상품B", 8),
                        tuple("상품B", 9)
                );

    }


    @DisplayName("특정 주문에 대하여 주문을 취소할 수 있다.")
    @Test
    void cancelOrder() {

        //given
        User userA = createUser("userA", "1234", UserRole.BUYER);
        userRepository.save(userA);
        Order order = createOrder(userA, OrderStatus.NEW, "test-detailAddress", "test-zipcode");
        Order savedOrder = orderRepository.save(order);

        //when
        orderService.cancelOrder(savedOrder.getId());

        //then
        assertThat(savedOrder.getOrderStatus()).isEqualTo(OrderStatus.CANCELED);

    }

    @DisplayName("배송완료된 주문의 경우 주문취소할 수 없다.")
    @Test
    void cancelOrderWithFinishedOrder() {

        //given
        User userA = createUser("userA", "1234", UserRole.BUYER);
        userRepository.save(userA);
        Order order = createOrder(userA, OrderStatus.DELIVERY_FINISHED, "test-detailAddress", "test-zipcode");
        Order savedOrder = orderRepository.save(order);

        //when, then
        assertThatThrownBy(
                () -> orderService.cancelOrder(savedOrder.getId())
        ).isInstanceOf(ApiException.class).hasMessage("주문을 수정할 수 없습니다.");
    }

    @DisplayName("하나의 주문과 연관된 주문상품 중 각 주문상품의 수량을 변경할수 있다.")
    @Test
    void updateOrderProduct() {

        //given
        User userA = createUser("userA", "1234", UserRole.BUYER);
        userRepository.save(userA);
        Order order = createOrder(userA, OrderStatus.NEW, "test-detailAddress", "test-zipcode");
        Order savedOrder = orderRepository.save(order);

        Product productA = createProduct(10000, "상품A");
        Product productB = createProduct(10000, "상품B");
        Product productC = createProduct(10000, "상품C");

        Product savedProductA = productRepository.save(productA);
        Product savedProductB = productRepository.save(productB);
        Product savedProductC = productRepository.save(productC);


        // 하나의 주문에 3개의 주문상품을 주문한다.
        OrderProduct orderProductA = createOrderProduct(savedOrder, savedProductA, 100);
        OrderProduct orderProductB = createOrderProduct(savedOrder, savedProductB, 20);
        OrderProduct orderProductC = createOrderProduct(savedOrder, savedProductC, 40);
        OrderProduct savedOrderProductA = orderProductRepository.save(orderProductA);
        OrderProduct savedOrderProductB = orderProductRepository.save(orderProductB);
        OrderProduct savedOrderProductC = orderProductRepository.save(orderProductC);

        // 3개의 주문상품 중 A의 수량을 10개로 변경한다.
        OrderProductUpdateRequest orderProductUpdateRequest1 = OrderProductUpdateRequest.builder()
                .productId(savedProductA.getId())
                .quantity(10).build();
        // 3개의 주문상품 중 B의 수량을 1개로 변경한다.
        OrderProductUpdateRequest orderProductUpdateRequest2 = OrderProductUpdateRequest.builder()
                .productId(savedProductB.getId())
                .quantity(1).build();

        OrderUpdateRequest orderUpdateRequest = OrderUpdateRequest.builder()
                .orderId(savedOrder.getId())
                .updateRequestList(List.of(orderProductUpdateRequest1, orderProductUpdateRequest2))
                .build();


        //when
        orderService.updateOrderProducts(savedOrder.getId(), orderUpdateRequest);


        //then
        assertThat(orderProductRepository.findById(savedOrderProductA.getId()).orElseThrow().getQuantity()).isEqualTo(10);
        assertThat(orderProductRepository.findById(savedOrderProductB.getId()).orElseThrow().getQuantity()).isEqualTo(1);

    }

    @DisplayName("배송완료된 주문의 경우 주문상품의 수량을 변경할 수 없다.")
    @Test
    void updateOrderProductsWithDeliveryFinished() {
        //given
        User userA = createUser("userA", "1234", UserRole.BUYER);
        userRepository.save(userA);
        Order order = createOrder(userA, OrderStatus.DELIVERY_FINISHED, "test-detailAddress", "test-zipcode");
        Order savedOrder = orderRepository.save(order);

        Product productA = createProduct(10000, "상품A");
        Product productB = createProduct(10000, "상품B");
        Product productC = createProduct(10000, "상품C");

        Product savedProductA = productRepository.save(productA);
        Product savedProductB = productRepository.save(productB);
        Product savedProductC = productRepository.save(productC);


        // 하나의 주문에 3개의 주문상품을 주문한다.
        OrderProduct orderProductA = createOrderProduct(savedOrder, savedProductA, 100);
        OrderProduct orderProductB = createOrderProduct(savedOrder, savedProductB, 20);
        OrderProduct orderProductC = createOrderProduct(savedOrder, savedProductC, 40);
        OrderProduct savedOrderProductA = orderProductRepository.save(orderProductA);
        OrderProduct savedOrderProductB = orderProductRepository.save(orderProductB);
        OrderProduct savedOrderProductC = orderProductRepository.save(orderProductC);

        // 3개의 주문상품 중 A의 수량을 10개로 변경한다.
        OrderProductUpdateRequest orderProductUpdateRequest1 = OrderProductUpdateRequest.builder()
                .productId(savedProductA.getId())
                .quantity(10).build();
        // 3개의 주문상품 중 B의 수량을 1개로 변경한다.
        OrderProductUpdateRequest orderProductUpdateRequest2 = OrderProductUpdateRequest.builder()
                .productId(savedProductB.getId())
                .quantity(1).build();

        OrderUpdateRequest orderUpdateRequest = OrderUpdateRequest.builder()
                .orderId(savedOrder.getId())
                .updateRequestList(List.of(orderProductUpdateRequest1, orderProductUpdateRequest2))
                .build();


        //when, then
        assertThatThrownBy(
                () -> orderService.updateOrderProducts(savedOrder.getId(), orderUpdateRequest)
        ).isInstanceOf(ApiException.class).hasMessage("주문을 수정할 수 없습니다.");



    }


}