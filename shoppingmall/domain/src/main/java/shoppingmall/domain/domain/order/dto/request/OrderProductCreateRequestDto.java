package shoppingmall.domain.domain.order.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Getter;
import shoppingmall.domain.domain.order.entity.Order;
import shoppingmall.domain.domain.order.entity.OrderProduct;
import shoppingmall.domain.domain.product.entity.Product;

@Getter
public class OrderProductCreateRequestDto {
    @NotNull(message = "상품번호가 입력되지 않았습니다.")
    private Long productId;
    @PositiveOrZero(message = "수량은 음수일 수 없습니다.")
    @Max(value = 9999L, message = "최대 수량을 9999개까지 입력가능합니다.")
    private int quantity;

    @Builder
    private OrderProductCreateRequestDto(Long productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public OrderProduct toEntity(Order order, Product product) {
        return OrderProduct.builder()
                .quantity(quantity)
                .order(order)
                .product(product)
                .build();
    }
}
