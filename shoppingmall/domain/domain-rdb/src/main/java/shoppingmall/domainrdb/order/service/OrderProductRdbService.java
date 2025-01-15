package shoppingmall.domainrdb.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import shoppingmall.domainrdb.common.annotation.DomainService;
import shoppingmall.domainrdb.mapper.OrderEntityMapper;
import shoppingmall.domainrdb.mapper.OrderProductEntityMapper;
import shoppingmall.domainrdb.order.domain.OrderProductDomain;
import shoppingmall.domainrdb.order.entity.OrderProduct;
import shoppingmall.domainrdb.order.repository.OrderProductRepository;
import shoppingmall.domainrdb.order.repository.OrderQueryRepository;
import shoppingmall.domainrdb.order.repository.OrderRepository;

import java.util.List;

@DomainService
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderProductRdbService {
    private final OrderProductRepository orderProductRepository;
    private final OrderQueryRepository orderQueryRepository;


    @Transactional
    public Long createOrderProduct(final OrderProductDomain orderProductDomain) {
        OrderProduct orderProductEntity = OrderProductEntityMapper.toOrderProductEntity(orderProductDomain);
        return orderProductRepository.save(orderProductEntity).getId();
    }

    @Transactional
    public void updateOrderProducts(final Long orderId, final Long productId, final int quantity) {
        // JPA의 변경감지를 이용한 업데이트
        orderQueryRepository.findOrderProductWithOrderIdAndProductId(orderId, productId).changeQuantity(quantity);

    }
}
