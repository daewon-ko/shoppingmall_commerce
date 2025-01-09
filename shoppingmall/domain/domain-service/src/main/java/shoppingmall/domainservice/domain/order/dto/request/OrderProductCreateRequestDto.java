package shoppingmall.domainservice.domain.order.dto.request;

import lombok.Builder;
import lombok.Getter;
import shoppingmall.domainrdb.mapper.ProductEntityMapper;
import shoppingmall.domainrdb.order.OrderProductDomain;

@Getter
public class OrderProductCreateRequestDto {
    private Long productId;
    private Integer quantity;
    private Integer price;

    @Builder
    private OrderProductCreateRequestDto(Long productId, Integer quantity, Integer price) {
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }

//    public OrderProductDomain toDomain() {
//        return new OrderProductDomain(productId, quantity);
//    }

}
