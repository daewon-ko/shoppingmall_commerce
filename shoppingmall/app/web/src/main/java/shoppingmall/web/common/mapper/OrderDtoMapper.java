package shoppingmall.web.common.mapper;

import shoppingmall.domainrdb.order.OrderDomain;
import shoppingmall.domainrdb.order.OrderStatus;
import shoppingmall.domainservice.domain.order.dto.request.OrderCreateRequestDto;
import shoppingmall.web.api.order.dto.request.OrderCreateRequestDto;

public class OrderDtoMapper {
    public OrderDomain toOrderDomain(final OrderCreateRequestDto orderCreateDto, final OrderStatus orderStatus) {
        return new OrderDomain(orderCreateDto.getZipCode(), orderCreateDto.getDetailAddress(), orderStatus, OrderProductDtoMapper.toOrderProductDomains(orderCreateDto.getDetailAddress(), orderCreateDto.getOrderProductRequestDtoList()));
    }
}
