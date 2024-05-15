package shppingmall.commerce.order.dto.request;

import lombok.Getter;
import shppingmall.commerce.cart.entity.Cart;
import shppingmall.commerce.order.OrderStatus;
import shppingmall.commerce.order.entity.Order;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class OrderCreateRequestDto {

    private Long cartId;

    private String zipCode;
    private String detailAddress;
    private List<OrderProductCreateRequestDto> orderProductRequestDtoList;

    public Order toEntity() {
        return Order.builder()
                .orderStatus(OrderStatus.NEW)   // 처음 주문이 들어오면 주문상태는 NEW로 고정
                .zipCode(getZipCode())
                .detailAddress(getDetailAddress())
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .build();
    }

    public Order toEntity(Cart cart) {
        return Order.builder()
                .zipCode(getZipCode())
                .detailAddress(getDetailAddress())
                .cart(cart)
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .orderStatus(OrderStatus.NEW)
                .build();

    }

}
