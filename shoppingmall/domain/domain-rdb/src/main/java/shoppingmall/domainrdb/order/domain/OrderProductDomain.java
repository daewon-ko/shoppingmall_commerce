package shoppingmall.domainrdb.order.domain;

import lombok.Builder;
import lombok.Getter;
import shoppingmall.domainrdb.product.domain.ProductDomain;
import shoppingmall.domainrdb.product.domain.ProductId;

@Getter
public class OrderProductDomain {
    private final OrderProductId orderProductId;
    private int quantity;
    private final ProductId productId;
    private final OrderId orderId;


    //TODO : OrderDomain을 추가해야할까?


    private OrderProductDomain(OrderProductId orderProductId, ProductId productId, OrderId orderId, int quantity) {
        this.orderProductId = orderProductId;
        this.productId = productId;
        this.orderId = orderId;
        this.quantity = quantity;
    }

    public static OrderProductDomain createForWrite(ProductId productId, OrderId orderId, int quantity) {
        return new OrderProductDomain(null, productId, orderId, quantity);
    }

    public static OrderProductDomain createForRead(Long id, ProductId productId, OrderId orderId, int quantity) {
        return new OrderProductDomain(OrderProductId.from(id), productId, orderId, quantity);
    }

    public void changeQuantity(int quantity) {
        this.quantity = quantity;
    }

}
