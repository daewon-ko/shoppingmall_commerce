package shppingmall.commerce.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shppingmall.commerce.cart.entity.Cart;
import shppingmall.commerce.cart.repository.CartRepository;
import shppingmall.commerce.order.OrderStatus;
import shppingmall.commerce.order.dto.request.*;
import shppingmall.commerce.order.dto.response.OrderProductCreateResponseDto;
import shppingmall.commerce.order.dto.response.OrderProductResponseDto;
import shppingmall.commerce.order.entity.Order;
import shppingmall.commerce.order.entity.OrderProduct;
import shppingmall.commerce.order.repository.OrderProductRepository;
import shppingmall.commerce.order.repository.OrderQueryRepository;
import shppingmall.commerce.order.repository.OrderRepository;
import shppingmall.commerce.product.entity.Product;
import shppingmall.commerce.product.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {
    //TODO : Service에서 타 Entity Service Layer를 참조하는게 적합한가 혹은 참조만 할것이라면 Repository Layer를 직접 참조하는 것도 괜찮은가?
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderProductRepository orderProductRepository;
    private final OrderQueryRepository orderQueryRepository;
    private final CartRepository cartRepository;


    @Transactional
    public List<OrderProductCreateResponseDto> createDirectOrder(OrderCreateRequestDto orderCreateRequestDto) {
        Order order = orderCreateRequestDto.toEntity();

        return createCommonOrder(orderCreateRequestDto, order);
    }


    @Transactional
    public List<OrderProductCreateResponseDto> createOrderCart(final OrderCreateRequestDto orderCartCreateRequestDto) {
        Long cartId = orderCartCreateRequestDto.getCartId();

        // DTO에서 cartId는 Validation할 수 없으므로 Service Logic에서 아래와 같이 검증
        if (cartId == null) {
            throw new IllegalArgumentException("카트번호가 없습니다. 재확인해주세요.");
        }
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
            OrderProduct savedOrderProduct = orderProductRepository.save(orderProduct);
            orderProductCreateResponseDtoList.add(OrderProductCreateResponseDto.of(savedOrderProduct));
        }
        return orderProductCreateResponseDtoList;
    }

    public Slice<OrderProductResponseDto> getOrderList(Long userId, OrderSearchCondition orderSearchCondition, Pageable pageable) {
        return orderQueryRepository.findOrderProducts(userId, orderSearchCondition, pageable);


    }

    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new IllegalStateException("해당하는 주문이 없습니다."));

        checkCancelValidation(order);
        order.cancelOrder();
        orderRepository.save(order);
    }

    private static void checkCancelValidation(Order order) {
        if (order.getOrderStatus().equals(OrderStatus.DELIVERY_FINISHED) || order.getOrderStatus().equals(OrderStatus.CANCELED)) {
            throw new IllegalStateException("주문을 취소할 수 없습니다.");
        }
    }

    @Transactional
    public void updateOrderProducts(Long orderId, OrderUpdateRequest orderUpdateRequest) {
        Order findOrder = orderRepository.findById(orderId).orElseThrow(() -> new IllegalStateException("해당하는 주문이 없습니다."));

        if (findOrder.getOrderStatus() == OrderStatus.DELIVERY_FINISHED || findOrder.getOrderStatus() == OrderStatus.CANCELED) {
            throw new IllegalStateException("주문을 수정할 수 없습니다.");
        }

        List<OrderProductUpdateRequest> updateRequestList = orderUpdateRequest.getUpdateRequestList();

        // TODO : 변경감지가 아닌 BulkUpdate가 더 적절할까?
        // save, saveAll이 더 좋은 방식일까? ..

        updateRequestList.forEach(orderProductUpdateRequest -> {
            // 특정 주문번호 및 특정 상품번호와 연관된 주문상품을 조회한다.
            OrderProduct orderProduct = orderQueryRepository.findOrderProductWithOrderIdAndProductId(orderId, orderProductUpdateRequest.getProductId());
//            // 주문상품의 수량을 수정한다.
            orderProduct.changeQuantity(orderProductUpdateRequest.getQuantity());

            orderProductRepository.save(orderProduct);
        });

    }
}
