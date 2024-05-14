package shppingmall.commerce.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shppingmall.commerce.order.dto.OrderCreateRequestDto;
import shppingmall.commerce.order.dto.OrderProductCreateRequestDto;
import shppingmall.commerce.order.entity.Order;
import shppingmall.commerce.order.entity.OrderProduct;
import shppingmall.commerce.order.repository.OrderProductRepository;
import shppingmall.commerce.order.repository.OrderRepository;
import shppingmall.commerce.product.entity.Product;
import shppingmall.commerce.product.repository.ProductRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    //TODO : Service에서 타 Entity Service Layer를 참조하는게 적합한가 혹은 참조만 할것이라면 Repository Layer를 직접 참조하는 것도 괜찮은가?
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderProductRepository orderProductRepository;


    public void createOrder(OrderCreateRequestDto orderCreateRequestDto) {

        // Order 생성
        Order order = orderCreateRequestDto.toEntity();
        orderRepository.save(order);

        // OrderProduct 생성
        List<OrderProductCreateRequestDto> orderProductRequestDtoList = orderCreateRequestDto.getOrderProductRequestDtoList();
        for (OrderProductCreateRequestDto orderProductRequestDto : orderProductRequestDtoList) {
            // TODO : 예외처리 정립 필요
            Product product = productRepository.findById(orderProductRequestDto.getProductId()).orElseThrow(() -> new IllegalArgumentException("해당 상품은 존재하지 않습니다."));
            OrderProduct orderProduct = orderProductRequestDto.toEntity(order, product);
            orderProductRepository.save(orderProduct);
        }
    }
}
