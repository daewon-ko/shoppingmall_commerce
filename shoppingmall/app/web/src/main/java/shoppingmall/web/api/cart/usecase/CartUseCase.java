package shoppingmall.web.api.cart.usecase;

import org.springframework.transaction.annotation.Transactional;
import shoppingmall.web.api.cart.dto.request.AddCartProductRequestDto;
import shoppingmall.web.api.cart.dto.request.AddCartRequestDto;
import shoppingmall.web.api.cart.dto.request.CreateCartRequestDto;
import shoppingmall.domainrdb.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import shoppingmall.web.api.cart.dto.response.AddCartProductResponseDto;
import shoppingmall.web.common.annotataion.Usecase;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Slf4j
@RequiredArgsConstructor
@Usecase
public class CartUseCase {
    private final CartService cartService;

    @Transactional
    public void createCart(final CreateCartRequestDto cartRequestDto) {
        cartService.createCart(cartRequestDto.getUserId());
    }

    /**
     * AddcartProductResposnePresentation
     * -> Controller (User에게 나갈 응답 객체구나)
     *
     *
     * POJO를 domain rdb 혹은 domain-service에서 받아서
     *
     * Controller에게 응답할 DTO를 컨버팅(?)을 이 Layer에서 해준다?
     *
     * Usecase의 응답값은 Presentation
     *
     *
     * Service 끼리 메서드를 호출할 수도 있는데, 이 메서드 호출하는 것을 POJO를 넘기는건 별로
     * domain-ser
     * @param cartRequestDto
     * @return
     */
    @Transactional
    public List<AddCartProductResponseDto> addProductsToCart(final AddCartRequestDto cartRequestDto) {
        Long cartId = cartRequestDto.getCartId();
        Map<Long, Integer> productQuantityMap = cartRequestDto.getCartProductRequestDtoList().stream().collect(Collectors.toMap(
                AddCartProductRequestDto::getProductId,
                AddCartProductRequestDto::getQuantity
        ));

        return cartService.addProductToCart(cartId, productQuantityMap).entrySet().stream()
                .map(entry -> AddCartProductResponseDto.builder()
                        .cartProductId(entry.getKey())
                        .quantity(entry.getValue()).build()).collect(Collectors.toList());

    }


}
