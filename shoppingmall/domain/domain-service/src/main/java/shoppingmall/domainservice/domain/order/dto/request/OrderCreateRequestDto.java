package shoppingmall.domainservice.domain.order.dto.request;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class OrderCreateRequestDto {

    private final Long cartId;
    private final Long userId;
    private final String zipCode;
    private final String detailAddress;

    private List<OrderProductCreateRequestDto> orderProductRequestDtoList;

    @Builder
    private OrderCreateRequestDto( Long cartId, Long userId, String zipCode, String detailAddress, List<OrderProductCreateRequestDto> orderProductRequestDtoList) {
        this.cartId = cartId;
        this.userId = userId;
        this.zipCode = zipCode;
        this.detailAddress = detailAddress;
        this.orderProductRequestDtoList = orderProductRequestDtoList;
    }


//    public Order toEntity() {
//        return Order.builder()
//                .orderStatus(OrderStatus.NEW)   // 처음 주문이 들어오면 주문상태는 NEW로 고정
//                .zipCode(getZipCode())
//                .detailAddress(getDetailAddress())
//                .build();
//    }

//    public Order toEntity(Cart cart) {
//        return Order.builder()
//                .zipCode(getZipCode())
//                .detailAddress(getDetailAddress())
//                .cart(cart)
//                .orderStatus(OrderStatus.NEW)
//                .build();
//
//    }

}
