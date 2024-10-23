package shppingmall.commerce.order.repository;

import com.querydsl.core.NonUniqueResultException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.transaction.annotation.Transactional;
import shppingmall.commerce.config.JpaConfig;
import shppingmall.commerce.order.OrderStatus;
import shppingmall.commerce.order.dto.request.OrderSearchCondition;
import shppingmall.commerce.order.dto.response.OrderProductResponseDto;
import shppingmall.commerce.order.entity.Order;
import shppingmall.commerce.order.entity.OrderProduct;
import shppingmall.commerce.product.entity.Product;
import shppingmall.commerce.product.repository.ProductRepository;
import shppingmall.commerce.user.entity.User;
import shppingmall.commerce.user.entity.UserRole;
import shppingmall.commerce.user.repository.UserRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static shppingmall.commerce.support.TestFixture.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)    // 실제 DB 이용
@Import({JpaConfig.class, OrderQueryRepository.class})
class OrderQueryRepositoryTest {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderQueryRepository orderQueryRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderProductRepository orderProductRepository;

    @AfterEach
    void tearDown() {

        orderProductRepository.deleteAllInBatch();
        orderRepository.deleteAllInBatch();
        productRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();
    }

    @DisplayName("주어진 사용자 Id와 주문상태 및 페이징 조건에 따라 주문 상품목록을 조회할 수 있다.")
    @Test
    @Transactional
    void findPagedOrderProductsByUserIdAndStatus() {
        //given
        User userA = createUser("test", "1234", UserRole.BUYER);
        userRepository.save(userA);
        Order order = createOrder(userA, OrderStatus.NEW, "test", "test");
        Order savedOrder = orderRepository.save(order);

        Product productA = createProduct(10000, "상품A");
        Product productB = createProduct(20000, "상품B");
        Product savedProductA = productRepository.save(productA);
        Product savedProductB = productRepository.save(productB);

        OrderProduct orderProduct1 = createOrderProduct(savedOrder, savedProductA, 100);

        OrderProduct orderProduct2 = createOrderProduct(savedOrder, savedProductB, 50);

        orderProductRepository.save(orderProduct1);
        orderProductRepository.save(orderProduct2);
        PageRequest pageRequest = PageRequest.of(0, 1);

        OrderSearchCondition orderStatus = OrderSearchCondition
                .builder()
                .orderStatus(OrderStatus.NEW)
                .build();


        //when
        Slice<OrderProductResponseDto> result = orderQueryRepository.findOrderProducts(userA.getId(), orderStatus, pageRequest);


        //then
        assertThat(result.getContent().size()).isEqualTo(1);

        assertThat(result.getContent())
                .extracting("productId", "productName", "price", "quantity")
                .contains(
                        tuple(savedProductA.getId(), savedProductA.getName(), savedProductA.getPrice(), 100)
                );


    }


    @DisplayName("주어진 사용자 Id와 주문상태 및 페이징 조건에 따라 다수의 주문 상품목록을 조회할 수 있다.")
    @Test
    void findManyPagedOrderProductsByUserIdAndStatus() {
        //given
        User userA = createUser("test", "1234", UserRole.BUYER);
        userRepository.save(userA);
        Order order = createOrder(userA, OrderStatus.NEW, "test", "test");
        Order savedOrder = orderRepository.save(order);

        Product productA = createProduct(10000, "상품A");
        Product productB = createProduct(20000, "상품B");
        Product savedProductA = productRepository.save(productA);
        Product savedProductB = productRepository.save(productB);


        for (int i = 0; i < 50; i++) {
            OrderProduct orderProductA = createOrderProduct(savedOrder, savedProductA, i);
            orderProductRepository.save(orderProductA);
        }

        for (int i = 0; i < 50; i++) {
            OrderProduct orderProductB = createOrderProduct(savedOrder, savedProductB, i);
            orderProductRepository.save(orderProductB);
        }

        PageRequest pageRequest = PageRequest.of(4, 5);

        OrderSearchCondition orderStatus = OrderSearchCondition
                .builder()
                .orderStatus(OrderStatus.NEW)
                .build();


        Slice<OrderProductResponseDto> result = orderQueryRepository.findOrderProducts(userA.getId(), orderStatus, pageRequest);


        //then

        assertThat(result.getContent())
                .extracting("productId", "productName", "price", "quantity")
                .containsExactlyInAnyOrder(
                        tuple(savedProductA.getId(), savedProductA.getName(), savedProductA.getPrice(), 20),
                        tuple(savedProductA.getId(), savedProductA.getName(), savedProductA.getPrice(), 21),
                        tuple(savedProductA.getId(), savedProductA.getName(), savedProductA.getPrice(), 22),
                        tuple(savedProductA.getId(), savedProductA.getName(), savedProductA.getPrice(), 23),
                        tuple(savedProductA.getId(), savedProductA.getName(), savedProductA.getPrice(), 24)

                );
    }

    @DisplayName("주문번호와 상품번호로 주문상품을 조회할 수 있다.")
    @Test
    void findOrderProductWithOrderIdAndProductId() {
        //given
        User userA = createUser("test", "1234", UserRole.BUYER);
        userRepository.save(userA);
        Order order = createOrder(userA, OrderStatus.NEW, "test", "test");
        Order savedOrder = orderRepository.save(order);

        Product productA = createProduct(10000, "상품A");
        Product productB = createProduct(20000, "상품B");
        Product savedProductA = productRepository.save(productA);
        Product savedProductB = productRepository.save(productB);

        OrderProduct orderProductA1 = createOrderProduct(savedOrder, savedProductA, 100);

        OrderProduct orderProductB = createOrderProduct(savedOrder, savedProductB, 50);

        orderProductRepository.save(orderProductA1);
        orderProductRepository.save(orderProductB);

        //when Order 중 상품 A에 관련된 주문상품을 조회한다.
        OrderProduct orderProduct = orderQueryRepository.findOrderProductWithOrderIdAndProductId(savedOrder.getId(), savedProductA.getId());

        //then
        assertThat(orderProduct).extracting(OrderProduct::getPrice, OrderProduct::getQuantity)
                .contains(10000, 100);

    }

    @DisplayName("존재하지 않는 주문번호와 상품번호로 조회 시 null을 반환한다.")
    @Test
    void findOrderProductWithInvalidNumber() {

        //given
        Long invalidOrderId = -1L;
        Long invalidProductId = -1L;

        //when
        OrderProduct orderProduct = orderQueryRepository.findOrderProductWithOrderIdAndProductId(invalidOrderId, invalidProductId);

        //then
        assertThat(orderProduct).isNull();

    }

    @DisplayName("동일한 주문번호와 상품번호에 여러 주문상품이 존재하면 예외가 발생한다.")
    @Test
    void findOrderProductWithDuplicate() {
        //given
        User userA = createUser("test", "1234", UserRole.BUYER);
        userRepository.save(userA);
        Order order = createOrder(userA, OrderStatus.NEW, "test", "test");
        Order savedOrder = orderRepository.save(order);

        Product productA = createProduct(10000, "상품A");
        Product savedProductA = productRepository.save(productA);

        OrderProduct orderProductA1 = createOrderProduct(savedOrder, savedProductA, 100);

        OrderProduct orderProductA2 = createOrderProduct(savedOrder, savedProductA, 50);

        orderProductRepository.save(orderProductA1);
        orderProductRepository.save(orderProductA2);

        //when & then
        assertThatThrownBy(() -> orderQueryRepository.findOrderProductWithOrderIdAndProductId(savedOrder.getId(), savedProductA.getId()))
                .isInstanceOf(NonUniqueResultException.class);


    }


}