package shoppingmall.domainrdb.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shoppingmall.common.exception.ApiException;
import shoppingmall.common.exception.domain.OrderErrorCode;
import shoppingmall.domainrdb.cart.repository.CartRepository;

import shoppingmall.domainrdb.mapper.OrderEntityMapper;
import shoppingmall.domainrdb.mapper.OrderProductEntityMapper;
import shoppingmall.domainrdb.mapper.UserEntityMapper;
import shoppingmall.domainrdb.order.domain.OrderDomain;
import shoppingmall.domainrdb.order.domain.OrderProductDomain;
import shoppingmall.domainrdb.order.domain.OrderStatus;
import shoppingmall.domainrdb.order.dto.OrderProductResponseDto;
import shoppingmall.domainrdb.order.dto.OrderSearchCondition;
import shoppingmall.domainrdb.order.entity.Order;
import shoppingmall.domainrdb.order.entity.OrderProduct;
import shoppingmall.domainrdb.order.repository.OrderProductRepository;
import shoppingmall.domainrdb.order.repository.OrderQueryRepository;
import shoppingmall.domainrdb.order.repository.OrderRepository;
import shoppingmall.domainrdb.product.entity.Product;
import shoppingmall.domainrdb.product.repository.ProductRepository;
import shoppingmall.domainrdb.user.UserDomain;
import shoppingmall.domainrdb.user.entity.User;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderRdbService {
    //TODO : Service에서 타 Entity Service Layer를 참조하는게 적합한가 혹은 참조만 할것이라면 Repository Layer를 직접 참조하는 것도 괜찮은가?
    private final OrderRepository orderRepository;
    private final OrderQueryRepository orderQueryRepository;


    @Transactional
    public Long createDirectOrder(final OrderDomain orderDomain) {
        return createCommonOrder(orderDomain);
    }


//    @Transactional
//    public List<OrderProductCreateResponseDto> createOrderCart(final OrderCreateRequestDto orderCartCreateRequestDto) {
//        Long cartId = orderCartCreateRequestDto.getCartId();
//
//
//        Cart cart = validateCartId(cartId);
//
//        Order order = orderCartCreateRequestDto.toEntity(cart);
//        return createCommonOrder(orderCartCreateRequestDto, order);
//
//    }


    public Slice<OrderProductResponseDto> getOrderList(Long userId, OrderSearchCondition orderSearchCondition, Pageable pageable) {
        Slice<OrderProduct> orderProducts = orderQueryRepository.findOrderProducts(userId, orderSearchCondition, pageable);

        boolean hasNext = orderProducts.hasNext();

        // OrderProduct를 OrderProductDomain으로 변환

        List<OrderProductResponseDto> orderProductResponseDtos = orderProducts.stream().map(orderProduct -> {

            OrderProductDomain orderProductDomain = OrderProductEntityMapper.toOrderProductDomain(orderProduct);
            Order order = orderProduct.getOrder();
            Product product = orderProduct.getProduct();

            OrderProductResponseDto orderProductResponseDto = OrderProductResponseDto.builder()
                    .productId(product.getId())
                    .orderId(order.getId())
                    .productName(product.getName())
                    .price(orderProduct.getPrice())
                    .quantity(orderProduct.getQuantity())
                    .build();

            return orderProductResponseDto;
        }).collect(Collectors.toUnmodifiableList());

        return new SliceImpl<>(orderProductResponseDtos, pageable, orderProducts.hasNext());

//        // DTO를 Domain 객체로 변환
//        orderProducts.stream().map(
//                orderProductResponseDto -> OrderEntityMapper.toOrderProductDomain(orderProductResponseDto)
//        )



    }


    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ApiException(OrderErrorCode.NOT_EXIST_ORDER));

        checkCancelValidation(order);
        order.cancelOrder();
        orderRepository.save(order);
    }


    // N+1 방지 위해 fetch join 사용하여 User 정보까지 조회
    public OrderDomain findById(Long orderId) {
        Order order = orderRepository.findByIdWithUser(orderId).orElseThrow(() -> new ApiException(OrderErrorCode.NOT_EXIST_ORDER));
        return OrderEntityMapper.toOrderDomain(order);
    }


    private Long createCommonOrder(final OrderDomain orderDomain) {

        // Order 생성(DB 저장)
        Order order = OrderEntityMapper.toOrderEntityWithOutCart(orderDomain);

        Order savedOrder = orderRepository.save(order);
        return savedOrder.getId();


    }


    private static void checkCancelValidation(Order order) {
        if (order.getOrderStatus().equals(OrderStatus.DELIVERY_FINISHED) || order.getOrderStatus().equals(OrderStatus.CANCELED)) {
            throw new ApiException(OrderErrorCode.ORDER_UPDATE_NOT_ALLOWED);
        }
    }


//    private Cart validateCartId(Long cartId) {
//        // DTO에서 cartId는 Validation할 수 없으므로 Service Logic에서 아래와 같이 검증
//        if (cartId == null) {
//            throw new ApiException(CartErrorCode.NO_EXIST_CART_NUMBER);
//        }
//        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new ApiException(CartErrorCode.NO_EXIST_CART));
//        return cart;
//    }

}
