package io.springbatch.domainrdb.order.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shoppingmall.domainrdb.domain.user.entity.User;
import shoppingmall.domainrdb.domain.user.entity.UserRole;

import static shoppingmall.domainrdb.support.TestFixture.*;

class OrderTest {

    @DisplayName("주문 상태를 취소할 수 있다.")
    @Test
    void cancelOrder() {
        //given
        User userA = createUser("userA", "1234", UserRole.BUYER);
        Order order = createOrder(userA, OrderStatus.NEW, "test-detailAddress", "test-zipcode");


        //when
        order.cancelOrder();

        //then
        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.CANCELED);

    }


}