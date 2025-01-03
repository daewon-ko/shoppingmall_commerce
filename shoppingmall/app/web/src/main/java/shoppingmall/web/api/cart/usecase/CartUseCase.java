package shoppingmall.web.api.cart.usecase;

import org.springframework.transaction.annotation.Transactional;
import shoppingmall.web.api.cart.dto.request.AddCartProductRequestDto;
import shoppingmall.web.api.cart.dto.request.AddCartRequestDto;
import shoppingmall.web.api.cart.dto.request.CreateCartRequestDto;
import shoppingmall.domainrdb.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import shoppingmall.common.annotation.Usecase;
import shoppingmall.web.api.cart.dto.response.AddCartProductResponseDto;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;



@Slf4j
@Usecase
@RequiredArgsConstructor
public class CartUseCase {
    private final CartService cartService;

    @Transactional
    public void createCart(final CreateCartRequestDto cartRequestDto) {
        cartService.createCart(cartRequestDto.getUserId());
    }

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
