package shppingmall.commerce.order.dto;

import lombok.Getter;
import shppingmall.commerce.cart.entity.Cart;
import shppingmall.commerce.order.OrderStatus;
import shppingmall.commerce.order.entity.Order;

import java.time.LocalDateTime;

@Getter
public class OrderCartCreateRequestDto {
    private Long cartId;
    private OrderCreateRequestDto orderCreateRequestDto;

    public Order toEntity(Cart cart) {
        return Order.builder()
                .zipCode(orderCreateRequestDto.getZipCode())
                .detailAddress(orderCreateRequestDto.getDetailAddress())
                .cart(cart)
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .orderStatus(OrderStatus.NEW)
                .build();

    }
}
