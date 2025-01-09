package shoppingmall.domainservice.domain.order.service;

import lombok.RequiredArgsConstructor;
import shoppingmall.common.exception.ApiException;
import shoppingmall.common.exception.domain.ProductErrorCode;
import shoppingmall.domainrdb.cart.service.CartProductRdbService;
import shoppingmall.domainrdb.cart.service.CartRdbService;
import shoppingmall.domainrdb.common.annotation.DomainService;
import shoppingmall.domainrdb.order.AddressDomain;
import shoppingmall.domainrdb.order.OrderDomain;
import shoppingmall.domainrdb.order.OrderProductDomain;
import shoppingmall.domainrdb.order.OrderStatus;
import shoppingmall.domainrdb.order.service.OrderProductRdbService;
import shoppingmall.domainrdb.order.service.OrderRdbService;
import shoppingmall.domainrdb.product.ProductDomain;
import shoppingmall.domainrdb.product.service.ProductRdbService;
import shoppingmall.domainrdb.user.UserDomain;
import shoppingmall.domainrdb.user.service.UserRdbService;
import shoppingmall.domainservice.domain.order.dto.request.OrderCreateRequestDto;
import shoppingmall.domainservice.domain.order.dto.response.OrderProductCreateResponseDto;

import java.util.List;
import java.util.stream.Collectors;

@DomainService
@RequiredArgsConstructor
public class OrderCreateService {
    private final OrderRdbService orderRdbService;
    private final CartProductRdbService cartProductRdbService;
    private final UserRdbService userRdbService;
    private final ProductRdbService productRdbService;
    private final OrderProductRdbService orderProductRdbService;

    public List<OrderProductCreateResponseDto> createOrderWithOutCart(final OrderCreateRequestDto orderCreateRequestDto) {


        OrderDomain orderDomain = makeOrder(orderCreateRequestDto);


        return orderCreateRequestDto.getOrderProductRequestDtoList().stream()
                .map(orderProductCreateRequestDto -> {
                    // product id로 product 조회
                    Long productId = orderProductCreateRequestDto.getProductId();
                    ProductDomain productDomain = productRdbService.getProductDomainByProductId(productId);

                    // DB에서 조회한 ProductDomain가격과 DTO에서 받은 가격이 다르면 예외를 던진다.

                    if (!productDomain.getPrice().equals(orderProductCreateRequestDto.getPrice())) {
                        throw new ApiException(ProductErrorCode.PRODUCT_PRICE_CHANGED);
                    }


                    OrderProductDomain orderProductDomain = OrderProductDomain.builder()
                            .productDomain(productDomain)
                            .orderDomain(orderDomain)
                            .quantity(orderProductCreateRequestDto.getQuantity()).build();

                    // OrderProduct 생성
                    Long orderProductId = orderProductRdbService.createOrderProduct(orderProductDomain);

                    return OrderProductCreateResponseDto.builder()
                            .orderProductId(orderProductId)
                            .quantity(orderProductCreateRequestDto.getQuantity()).build();

                }).collect(Collectors.toUnmodifiableList());


    }



    /**
     * Cart에 있는 특정 상품을 주문하는 API라고 한다.
     * 1. cartID로 CartProduct를 조회해서 List로 quantity와 productId를 가져온다.
     * 2. cartId로 CartDomain을 조회한다.
     * 3. OrderDomain을 생성한다.
     * 4. productId로 ProductDomain을 조회한다.
     * 5. OrderProductDomain을 생성한다.
     *
     * @param orderCreateRequestDto
     * @return
     */


    public List<OrderProductCreateResponseDto> createOrderWithCart(final OrderCreateRequestDto orderCreateRequestDto) {


        OrderDomain orderDomain = makeOrder(orderCreateRequestDto);

        // cartId로 cartProduct List 조회

        return cartProductRdbService.findAllCartProductsByCartId(orderCreateRequestDto.getCartId())
                .stream()
                .map(cartProductDomain -> {


                    // DB에서 조회한 ProductDomain가격과 DTO에서 받은 가격이 다르면 예외를 던진다.
                    if (!cartProductDomain.getProductDomain().getPrice()
                            .equals(orderCreateRequestDto.getOrderProductRequestDtoList()
                                    .stream()
                                    .filter(dto -> dto.getProductId().equals(cartProductDomain.getProductDomain().getId()))
                                    .findFirst()
                                    .orElseThrow(() -> new ApiException(ProductErrorCode.PRODUCT_NOT_FOUND))
                                    .getPrice())){
                        throw new ApiException(ProductErrorCode.PRODUCT_PRICE_CHANGED);
                    }



                    OrderProductDomain orderProductDomain = OrderProductDomain.builder()
                            .orderDomain(orderDomain)
                            .productDomain(cartProductDomain.getProductDomain())
                            .quantity(cartProductDomain.getQuantity())
                            .build();
                    // OrderProduct 생성
                    Long orderProductId = orderProductRdbService.createOrderProduct(orderProductDomain);


                    // CartId에 해당하는 CartProduct 삭제
                    cartProductRdbService.deleteAllByCartId(orderCreateRequestDto.getCartId());





                    return OrderProductCreateResponseDto.builder()
                            .orderProductId(orderProductId)
                            .quantity(orderProductDomain.getQuantity()).build();

                }).collect(Collectors.toUnmodifiableList());


    }




    private OrderDomain makeOrder(OrderCreateRequestDto orderCreateRequestDto) {
        UserDomain userDomain = userRdbService.findByUserId(orderCreateRequestDto.getUserId());

        AddressDomain addressDomain = AddressDomain.builder()
                .zipCode(orderCreateRequestDto.getZipCode())
                .detailAddress(orderCreateRequestDto.getDetailAddress()).build();


        OrderDomain orderDomain = OrderDomain.builder()
                .orderStatus(OrderStatus.NEW)
                .addressDomain(addressDomain)
                .userDomain(userDomain)
                .build();

        // Order 생성
        return orderRdbService.createDirectOrder(orderDomain);
    }
}