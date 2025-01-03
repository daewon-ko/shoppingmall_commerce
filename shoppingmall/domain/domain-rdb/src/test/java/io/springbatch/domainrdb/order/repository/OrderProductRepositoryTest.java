package io.springbatch.domainrdb.order.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import shoppingmall.common.exception.ApiException;
import shoppingmall.common.exception.domain.OrderErrorCode;
import shoppingmall.domainrdb.domain.order.entity.Order;
import shoppingmall.domainrdb.domain.order.entity.OrderProduct;
import shoppingmall.domainrdb.domain.order.entity.OrderStatus;
import shoppingmall.domainrdb.domain.product.entity.Product;
import shoppingmall.domainrdb.domain.product.repository.ProductRepository;
import shoppingmall.domainrdb.support.RepositoryTestSupport;

import static org.assertj.core.api.Assertions.assertThat;
import static shoppingmall.domainrdb.support.TestFixture.*;

class OrderProductRepositoryTest extends RepositoryTestSupport {

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