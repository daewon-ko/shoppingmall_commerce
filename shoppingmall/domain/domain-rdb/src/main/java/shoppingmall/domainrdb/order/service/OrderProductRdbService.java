package shoppingmall.domainrdb.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import shoppingmall.domainrdb.common.annotation.DomainService;
import shoppingmall.domainrdb.mapper.OrderProductEntityMapper;
import shoppingmall.domainrdb.order.OrderProductDomain;
import shoppingmall.domainrdb.order.entity.OrderProduct;
import shoppingmall.domainrdb.order.repository.OrderProductRepository;

@DomainService
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderProductRdbService {
    private final OrderProductRepository orderProductRepository;


    @Transactional
    public Long createOrderProduct(OrderProductDomain orderProductDomain) {
        OrderProduct orderProductEntity = OrderProductEntityMapper.toOrderProductEntity(orderProductDomain);
        return orderProductRepository.save(orderProductEntity).getId();
    }
}
