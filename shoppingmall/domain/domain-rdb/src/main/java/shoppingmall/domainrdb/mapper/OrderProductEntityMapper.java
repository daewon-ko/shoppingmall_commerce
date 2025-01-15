package shoppingmall.domainrdb.mapper;

import shoppingmall.domainrdb.order.domain.OrderId;
import shoppingmall.domainrdb.order.domain.OrderProductDomain;
import shoppingmall.domainrdb.order.entity.Order;
import shoppingmall.domainrdb.order.entity.OrderProduct;
import shoppingmall.domainrdb.product.domain.ProductId;
import shoppingmall.domainrdb.product.entity.Product;

public class OrderProductEntityMapper {
    public static OrderProduct toOrderProductEntity(final OrderProductDomain orderProductDomain) {
        OrderId orderId = orderProductDomain.getOrderId();
        ProductId productId = orderProductDomain.getProductId();

        Order order = Order.fromOrderId(orderId);
        Product product = Product.fromProductId(productId);

        return OrderProduct.builder()
                .order(order)
                .product(product)
                .quantity(orderProductDomain.getQuantity())
                .build();
    }

    public static OrderProductDomain toOrderProductDomain(final OrderProduct orderProduct) {
        return OrderProductDomain.createForRead(orderProduct.getId(), ProductId.from(orderProduct.getProduct().getId()), OrderId.from(orderProduct.getOrder().getId()), orderProduct.getQuantity());
    }
}
