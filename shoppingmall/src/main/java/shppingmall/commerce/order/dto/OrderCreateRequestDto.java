package shppingmall.commerce.order.dto;

import lombok.Getter;
import shppingmall.commerce.order.OrderStatus;
import shppingmall.commerce.order.entity.Order;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class OrderCreateRequestDto {

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

}
