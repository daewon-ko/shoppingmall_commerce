package shoppingmall.domainrdb.order.factory;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import shoppingmall.common.exception.ApiException;
import shoppingmall.common.exception.domain.OrderErrorCode;
import shoppingmall.common.exception.domain.ProductErrorCode;
import shoppingmall.domainrdb.order.domain.OrderProductDomain;
import shoppingmall.domainrdb.order.entity.Order;
import shoppingmall.domainrdb.order.entity.OrderProduct;
import shoppingmall.domainrdb.order.repository.OrderRepository;
import shoppingmall.domainrdb.product.entity.Product;
import shoppingmall.domainrdb.product.repository.ProductRepository;

@Component
@RequiredArgsConstructor
public class OrderProductFactory {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderProduct createOrderProduct(OrderProductDomain orderProductDomain) {
        Order order = orderRepository.findById(orderProductDomain.getOrderId().getValue())
                .orElseThrow(() -> new ApiException(OrderErrorCode.NOT_EXIST_ORDER));

        Product product = productRepository.findById(orderProductDomain.getProductId().getValue())
                .orElseThrow(() -> new ApiException(ProductErrorCode.PRODUCT_NOT_FOUND));

        return OrderProduct.builder()
                .order(order)
                .product(product)
                .price(product.getPrice())
                .quantity(orderProductDomain.getQuantity())
                .build();
    }
}
