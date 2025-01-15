package shoppingmall.domainrdb.order.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import shoppingmall.domainrdb.order.domain.OrderProductDomain;
import shoppingmall.domainrdb.product.domain.ProductDomain;

@Getter
@ToString
public class OrderProductResponseDto {
    private Long orderId;
    private Long productId;
    private String productName;
    private Integer price;
    private Integer quantity;

    @Builder
    public OrderProductResponseDto(Long orderId, Long productId, String productName, Integer price, Integer quantity) {
        this.orderId = orderId;
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
    }

    public OrderProductResponseDto(final OrderProductDomain orderProductDomain, final ProductDomain productDomain) {
        this.orderId = orderProductDomain.getOrderId().getValue();
        this.productId = orderProductDomain.getOrderProductId().getValue();
        this.productName = productDomain.getName();
        this.price = productDomain.getPrice();
        this.quantity = orderProductDomain.getQuantity();
    }
}
