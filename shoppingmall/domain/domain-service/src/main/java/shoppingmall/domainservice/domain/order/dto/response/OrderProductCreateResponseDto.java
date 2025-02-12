package shoppingmall.domainservice.domain.order.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class OrderProductCreateResponseDto {
    private final Long orderProductId;
    private final int quantity;

}
