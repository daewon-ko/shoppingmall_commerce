package shoppingmall.web.api.order.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.transaction.annotation.Transactional;
import shoppingmall.domainrdb.order.dto.OrderProductResponseDto;
import shoppingmall.domainrdb.order.dto.OrderSearchCondition;
import shoppingmall.domainrdb.order.service.OrderRdbService;
import shoppingmall.domainservice.common.type.request.RequestType;
import shoppingmall.domainservice.domain.order.dto.request.OrderCreateRequestDto;
import shoppingmall.domainservice.domain.order.dto.request.OrderUpdateRequest;
import shoppingmall.domainservice.domain.order.dto.response.OrderProductCreateResponseDto;
import shoppingmall.domainservice.domain.order.service.OrderCreateService;
import shoppingmall.domainservice.domain.order.service.OrderSearchService;
import shoppingmall.domainservice.domain.order.service.OrderUpdateService;
import shoppingmall.web.common.annotataion.Usecase;
import shoppingmall.web.common.validation.order.OrderValidator;

import java.util.List;

@Usecase
@RequiredArgsConstructor
public class OrderUsecase {
    private final OrderCreateService orderCreateService;
    private final OrderSearchService orderSearchService;
    private final OrderUpdateService orderUpdateService;
    private final OrderRdbService orderRdbService;
    private final List<OrderValidator> orderValidators;


    @Transactional
    public List<OrderProductCreateResponseDto> createDirectOrder(final OrderCreateRequestDto orderCreateDto) {

        validateRequest(orderCreateDto, RequestType.ORDER_CREATE);
        return orderCreateService.createOrderWithOutCart(orderCreateDto);

    }

    @Transactional
    public List<OrderProductCreateResponseDto> createOrderWithCart(final OrderCreateRequestDto orderCreateRequestDto) {
        validateRequest(orderCreateRequestDto, RequestType.ORDER_CREATE);
        return orderCreateService.createOrderWithCart(orderCreateRequestDto);
    }



    public Slice<OrderProductResponseDto> getOrderList(final Long userId, final OrderSearchCondition orderSearchCondition, final Pageable pageable) {

        return orderSearchService.searchOrders(userId, orderSearchCondition, pageable);
        // OrderProductDomain을
    }

    @Transactional
    public void cancelOrder(final Long orderId) {
        orderRdbService.cancelOrder(orderId);

    }

    @Transactional
    public void updateOrder(final OrderUpdateRequest orderUpdateRequest) {
        validateRequest(orderUpdateRequest, RequestType.ORDER_UPDATE);
        orderUpdateService.updateOrderProduct(orderUpdateRequest);
    }

    // 공통 Validation 작업
    private <T> void validateRequest(T requestDto, RequestType requestType) {
        orderValidators.forEach(orderValidator -> {
            if (orderValidator.isAcceptable(requestType)) {
                orderValidator.validate(requestDto);
            }
        });
    }


}
