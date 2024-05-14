package shppingmall.commerce.order.dto;

import lombok.Getter;
import shppingmall.commerce.order.entity.Order;
import shppingmall.commerce.order.entity.OrderProduct;
import shppingmall.commerce.product.entity.Product;

@Getter
public class OrderProductCreateRequestDto {
    private Long productId;
    private int quantity;

    public OrderProduct toEntity(Order order, Product product) {
        return OrderProduct.builder()
                .quantity(quantity)
                .order(order)
                .product(product)
                .build();
    }
}
