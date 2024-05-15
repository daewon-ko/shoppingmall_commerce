package shppingmall.commerce.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shppingmall.commerce.cart.entity.Cart;
import shppingmall.commerce.cart.repository.CartRepository;
import shppingmall.commerce.order.dto.request.OrderCreateRequestDto;
import shppingmall.commerce.order.dto.request.OrderProductCreateRequestDto;
import shppingmall.commerce.order.dto.response.OrderProductCreateResponseDto;
import shppingmall.commerce.order.entity.Order;
import shppingmall.commerce.order.entity.OrderProduct;
import shppingmall.commerce.order.repository.OrderProductRepository;
import shppingmall.commerce.order.repository.OrderRepository;
import shppingmall.commerce.product.entity.Product;
import shppingmall.commerce.product.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    //TODO : Service에서 타 Entity Service Layer를 참조하는게 적합한가 혹은 참조만 할것이라면 Repository Layer를 직접 참조하는 것도 괜찮은가?
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderProductRepository orderProductRepository;
    private final CartRepository cartRepository;


    @Transactional
    public List<OrderProductCreateResponseDto> createDirectOrder(OrderCreateRequestDto orderCreateRequestDto) {
        Order order  = orderCreateRequestDto.toEntity();

        return createCommonOrder(orderCreateRequestDto, order);
    }


    @Transactional
    public List<OrderProductCreateResponseDto> createOrderCart(final OrderCreateRequestDto orderCartCreateRequestDto) {
        Long cartId = orderCartCreateRequestDto.getCartId();
        // TODO : 예외처리 필요, Cart는 Nullable하다. 그러나 null로 Exception이 발생할 경우에는?
        // 그렇지만, 해당 Order의 경우 반드시 Cart가 존재해야한다. (장바구니가 있는 주문을 상정하기 때문에)
        // 그럼에도 아래와 같은 로직은 적절하다고 할 수 있을까?
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new IllegalArgumentException("해당하는 카트가 존재하지 않습니다."));

        Order order = orderCartCreateRequestDto.toEntity(cart);
        return createCommonOrder(orderCartCreateRequestDto, order);

    }

    private List<OrderProductCreateResponseDto> createCommonOrder(final OrderCreateRequestDto orderCreateRequestDto, final Order order) {
        // Order 생성(DB 저장)
        orderRepository.save(order);

        // OrderProduct 생성
        List<OrderProductCreateRequestDto> orderProductRequestDtoList = orderCreateRequestDto.getOrderProductRequestDtoList();
        // 응답용 객체를 담은 List 생성
        List<OrderProductCreateResponseDto> orderProductCreateResponseDtoList = new ArrayList<>();

        for (OrderProductCreateRequestDto orderProductRequestDto : orderProductRequestDtoList) {
            // TODO : 예외처리 정립 필요
            Product product = productRepository.findById(orderProductRequestDto.getProductId()).orElseThrow(() -> new IllegalArgumentException("해당 상품은 존재하지 않습니다."));
            OrderProduct orderProduct = orderProductRequestDto.toEntity(order, product);
            orderProductRepository.save(orderProduct);
            orderProductCreateResponseDtoList.add(OrderProductCreateResponseDto.of(orderProduct));
        }
        return orderProductCreateResponseDtoList;
    }
}
