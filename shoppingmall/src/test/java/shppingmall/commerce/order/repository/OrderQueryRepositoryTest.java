package shppingmall.commerce.order.repository;

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
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import shppingmall.commerce.config.JpaConfig;
import shppingmall.commerce.order.OrderStatus;
import shppingmall.commerce.order.dto.request.OrderProductCreateRequestDto;
import shppingmall.commerce.order.dto.request.OrderSearchCondition;
import shppingmall.commerce.order.dto.response.OrderProductResponseDto;
import shppingmall.commerce.order.entity.Order;
import shppingmall.commerce.order.entity.OrderProduct;
import shppingmall.commerce.product.entity.Product;
import shppingmall.commerce.product.repository.ProductRepository;
import shppingmall.commerce.support.TestFixture;
import shppingmall.commerce.user.entity.User;
import shppingmall.commerce.user.entity.UserRole;
import shppingmall.commerce.user.repository.UserRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static shppingmall.commerce.support.TestFixture.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)    // DB 교
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

        OrderSearchCondition orderStatus = OrderSearchCondition
                .builder()
                .pageable(PageRequest.of(0, 1))
                .orderStatus(OrderStatus.NEW)
                .build();


        //when
        Slice<OrderProductResponseDto> result = orderQueryRepository.findOrderProducts(userA.getId(), orderStatus);


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
    void findManyPagedOrderProductsByUserIdAndStatusst() {
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

        for (int i = 0; i < 50; i++) {
            OrderProduct orderProductA = createOrderProduct(savedOrder, savedProductA, i);
            orderProductRepository.save(orderProductA);
        }

        for (int i = 0; i < 50; i++) {
            OrderProduct orderProductB = createOrderProduct(savedOrder, savedProductB, i);
            orderProductRepository.save(orderProductB);
        }

        OrderSearchCondition orderStatus = OrderSearchCondition
                .builder()
                .pageable(PageRequest.of(4, 5))
                .orderStatus(OrderStatus.NEW)
                .build();


        Slice<OrderProductResponseDto> result = orderQueryRepository.findOrderProducts(userA.getId(), orderStatus);


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



}