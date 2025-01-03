package shoppingmall.domainrdb.order.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class OrderProductResponseDto {
    private Long orderId;
    private Long productId;
    private String productName;
    private Integer price;
    private Integer quantity;

    @QueryProjection
    @Builder
    public OrderProductResponseDto(Long orderId, Long productId, String productName, Integer price, Integer quantity) {
        this.orderId = orderId;
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
    }
}
