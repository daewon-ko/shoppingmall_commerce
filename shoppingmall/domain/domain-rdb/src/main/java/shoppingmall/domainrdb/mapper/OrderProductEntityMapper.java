package shoppingmall.domainrdb.mapper;

import shoppingmall.domainrdb.order.OrderProductDomain;
import shoppingmall.domainrdb.order.entity.Order;
import shoppingmall.domainrdb.order.entity.OrderProduct;

public class OrderProductEntityMapper {
    public static OrderProduct toOrderProductEntity(final OrderProductDomain orderProductDomain) {
        return OrderProduct.builder()
                .order(OrderEntityMapper.toOrderEntity(orderProductDomain.getOrderDomain()))
                .product(ProductEntityMapper.toProductEntity(orderProductDomain.getProductDomain()))
                .quantity(orderProductDomain.getQuantity())
                .build();
    }
}
