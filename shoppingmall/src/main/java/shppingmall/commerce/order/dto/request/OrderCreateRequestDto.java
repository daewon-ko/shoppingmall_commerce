package shppingmall.commerce.order.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import shppingmall.commerce.cart.entity.Cart;
import shppingmall.commerce.order.OrderStatus;
import shppingmall.commerce.order.entity.Order;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class OrderCreateRequestDto {

    @NotNull(message = "카트번호가 선택되지 않았습니다.")
    private Long cartId;

    @NotBlank(message = "주소가 입력되지 않았습니다.")
    private String zipCode;
    @NotBlank(message = "세부 주소가 입력되지 않았습니다.")
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
