package shoppingmall.core.domain.order.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import shoppingmall.core.domain.order.entity.Order;
import shoppingmall.core.domain.cart.entity.Cart;
import shoppingmall.core.domain.order.entity.OrderStatus;

import java.util.List;

@Getter
public class OrderCreateRequestDto {

    private Long cartId;

    @NotBlank(message = "주소가 입력되지 않았습니다.")
    private String zipCode;
    @NotBlank(message = "세부 주소가 입력되지 않았습니다.")
    private String detailAddress;

    private List<OrderProductCreateRequestDto> orderProductRequestDtoList;


    @Builder
    private OrderCreateRequestDto(Long cartId, String zipCode, String detailAddress, List<OrderProductCreateRequestDto> orderProductRequestDtoList) {
        this.cartId = cartId;
        this.zipCode = zipCode;
        this.detailAddress = detailAddress;
        this.orderProductRequestDtoList = orderProductRequestDtoList;
    }




    public Order toEntity() {
        return Order.builder()
                .orderStatus(OrderStatus.NEW)   // 처음 주문이 들어오면 주문상태는 NEW로 고정
                .zipCode(getZipCode())
                .detailAddress(getDetailAddress())
                .build();
    }

    public Order toEntity(Cart cart) {
        return Order.builder()
                .zipCode(getZipCode())
                .detailAddress(getDetailAddress())
                .cart(cart)
                .orderStatus(OrderStatus.NEW)
                .build();

    }

}
