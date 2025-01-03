package shoppingmall.domainrdb.domain.order.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import shoppingmall.domainrdb.domain.order.entity.OrderProduct;

@Getter
@AllArgsConstructor
@Builder
public class OrderProductCreateResponseDto {
    private final Long orderProductId;
    private final int quantity;

    public static OrderProductCreateResponseDto of(OrderProduct orderProduct) {
        return OrderProductCreateResponseDto.builder()
                .orderProductId(orderProduct.getId())
                .quantity(orderProduct.getQuantity())
                .build();
    }


}
