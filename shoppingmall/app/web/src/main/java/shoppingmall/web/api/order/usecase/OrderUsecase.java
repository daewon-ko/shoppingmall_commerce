package shoppingmall.web.api.order.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.transaction.annotation.Transactional;
import shoppingmall.domainrdb.order.dto.OrderProductResponseDto;
import shoppingmall.domainrdb.order.dto.OrderSearchCondition;
import shoppingmall.domainrdb.order.service.OrderRdbService;
import shoppingmall.domainservice.domain.order.dto.request.OrderCreateRequestDto;
import shoppingmall.domainservice.domain.order.dto.request.OrderUpdateRequest;
import shoppingmall.domainservice.domain.order.dto.response.OrderProductCreateResponseDto;
import shoppingmall.domainservice.domain.order.service.OrderCreateService;
import shoppingmall.domainservice.domain.order.service.OrderSearchService;
import shoppingmall.domainservice.domain.order.service.OrderUpdateService;
import shoppingmall.web.common.annotataion.Usecase;
import shoppingmall.web.common.validation.order.OrderCreateValidator;
import shoppingmall.web.common.validation.order.OrderProductValidator;
import shoppingmall.web.common.validation.order.OrderUpdateValidator;

import java.util.List;

@Usecase
@RequiredArgsConstructor
public class OrderUsecase {
    private final OrderCreateService orderCreateService;
    private final OrderSearchService orderSearchService;
    private final OrderCreateValidator orderCreateValidator;
    private final OrderProductValidator orderProductValidator;
    private final OrderUpdateValidator orderUpdateValidator;
    private final OrderUpdateService orderUpdateService;
    private final OrderRdbService orderRdbService;

    @Transactional
    public List<OrderProductCreateResponseDto> createDirectOrder(final OrderCreateRequestDto orderCreateDto) {
        // TODO : Usecase에서 Validation 작업해주는게 좋을까?
        // Validation
        orderCreateValidator.validate(orderCreateDto);
        orderProductValidator.validate(orderCreateDto.getOrderProductRequestDtoList());


        return orderCreateService.createOrderWithOutCart(orderCreateDto);

    }

    @Transactional
    public List<OrderProductCreateResponseDto> createOrderWithCart(final OrderCreateRequestDto orderCreateRequestDto) {
        // Validation
        orderCreateValidator.validate(orderCreateRequestDto);
        orderProductValidator.validate(orderCreateRequestDto.getOrderProductRequestDtoList());

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
        orderUpdateValidator.validate(orderUpdateRequest.getOrderId(), orderUpdateRequest.getUpdateRequestList());
        orderUpdateService.updateOrderProduct(orderUpdateRequest);
    }
}
