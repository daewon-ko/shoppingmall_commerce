package shoppingmall.web.api.order.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import shoppingmall.domainservice.domain.order.dto.request.OrderCreateRequestDto;
import shoppingmall.domainservice.domain.order.dto.request.OrderProductCreateRequestDto;
import shoppingmall.domainservice.domain.order.dto.response.OrderProductCreateResponseDto;
import shoppingmall.domainservice.domain.order.service.OrderCreateService;
import shoppingmall.web.common.annotataion.Usecase;
import shoppingmall.web.common.validation.order.OrderCreateValidator;
import shoppingmall.web.common.validation.order.OrderProductValidator;

import java.util.List;

@Usecase
@RequiredArgsConstructor
public class OrderUsecase {
    private final OrderCreateService orderCreateService;
    private final OrderCreateValidator orderCreateValidator;
    private final OrderProductValidator orderProductValidator;

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
}
