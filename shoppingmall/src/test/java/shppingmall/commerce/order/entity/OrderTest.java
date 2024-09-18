package shppingmall.commerce.order.entity;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shppingmall.commerce.order.OrderStatus;
import shppingmall.commerce.user.entity.User;
import shppingmall.commerce.user.entity.UserRole;

import static org.assertj.core.api.Assertions.*;
import static shppingmall.commerce.support.TestFixture.*;

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
        assertThat(order.orderStatus).isEqualTo(OrderStatus.CANCELED);

    }


}