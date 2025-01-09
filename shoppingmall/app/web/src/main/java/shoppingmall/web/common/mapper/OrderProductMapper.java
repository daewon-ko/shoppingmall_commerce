//package shoppingmall.web.common.mapper;
//
//import shoppingmall.domainrdb.order.OrderProductDomain;
//import shoppingmall.domainservice.domain.order.dto.request.OrderProductCreateRequestDto;
//import shoppingmall.web.api.order.dto.request.OrderProductCreateRequestDto;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//public class OrderProductMapper {
//    public static List<OrderProductDomain> toOrderProductDomains(List<OrderProductCreateRequestDto> orderProductCreateRequestDtos) {
//        return orderProductCreateRequestDtos.stream()
//                .map(orderProductCreateRequestDto -> new OrderProductDomain(orderProductCreateRequestDto.getQuantity(), ProductDtoMapper.toProductDomain(orderProductCreateRequestDto.getProductId(), orderProductCreateRequestDto.getQuantity())))
//                .collect(Collectors.toList());
//    }
//}
