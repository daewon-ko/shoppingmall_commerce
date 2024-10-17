package shppingmall.commerce.order.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.web.bind.annotation.ResponseStatus;
import shppingmall.commerce.global.exception.ApiException;
import shppingmall.commerce.global.exception.domain.OrderErrorCode;
import shppingmall.commerce.order.OrderStatus;
import shppingmall.commerce.order.dto.request.OrderUpdateRequest;
import shppingmall.commerce.order.entity.Order;
import shppingmall.commerce.order.entity.OrderProduct;
import shppingmall.commerce.product.entity.Product;
import shppingmall.commerce.product.repository.ProductRepository;
import shppingmall.commerce.support.TestFixture;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static shppingmall.commerce.support.TestFixture.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)    // DB 교
class OrderProductRepositoryTest {

    @Autowired
    private OrderProductRepository orderProductRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;
    @PersistenceContext
    EntityManager entityManager;

    @DisplayName("")
    @Test
    void updateOrderProducts() {

        //given
        Order order = createOrder(null, OrderStatus.NEW, "test", "test");
        Order savedOrder = orderRepository.save(order);
        Product productA = createProduct(10000, "상품A");
        Product productB = createProduct(10000, "상품B");
        Product savedProductA = productRepository.save(productA);
        Product savedProductB = productRepository.save(productB);
        OrderProduct orderProductA = createOrderProduct(savedOrder, savedProductA, 100);
        OrderProduct orderProductB = createOrderProduct(savedOrder, savedProductB, 100);

        orderProductRepository.save(orderProductA);
        orderProductRepository.save(orderProductB);


        //when
        int result = orderProductRepository.updateOrderProductQuantity(savedOrder.getId(), savedProductA.getId(), 20);

        entityManager.flush();
        entityManager.clear();

        //then

        assertThat(result).isEqualTo(1);
        assertThat(orderProductRepository.findById(orderProductA.getId()).orElseThrow(() -> new ApiException(OrderErrorCode.NOT_EXIST_ORDER_PRODUCT))
                .getQuantity()).isEqualTo(20);


    }


}