package shppingmall.commerce.cart.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shppingmall.commerce.cart.dto.response.AddCartProductResponseDto;
import shppingmall.commerce.cart.dto.request.AddCartRequestDto;
import shppingmall.commerce.cart.dto.request.CreateCartRequestDto;
import shppingmall.commerce.cart.entity.Cart;
import shppingmall.commerce.cart.entity.CartProduct;
import shppingmall.commerce.cart.repository.CartProductRepository;
import shppingmall.commerce.cart.repository.CartRepository;
import shppingmall.commerce.global.exception.ApiException;
import shppingmall.commerce.global.exception.domain.CartErrorCode;
import shppingmall.commerce.global.exception.domain.ProductErrorCode;
import shppingmall.commerce.global.exception.domain.UserErrorCode;
import shppingmall.commerce.product.entity.Product;
import shppingmall.commerce.product.repository.ProductRepository;
import shppingmall.commerce.user.entity.User;
import shppingmall.commerce.user.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final CartProductRepository cartProductRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;


    //Cart에 상품을 추가한다.
    @Transactional
    public List<AddCartProductResponseDto> addProductToCart(final AddCartRequestDto cartRequestDto) {
        // TODO : 예외정의 필요
        Cart cart = cartRepository.findById(cartRequestDto.getCartId()).orElseThrow(() -> new ApiException(CartErrorCode.NO_EXIST_CART));
        return cartRequestDto.getCartProductRequestDtoList().stream()
                .map(addCartProductRequestDto -> {
                    Product product = productRepository.findById(addCartProductRequestDto.getProductId())
                            .orElseThrow(() -> new ApiException(ProductErrorCode.PRODUCT_NOT_FOUND));
                    int quantity = addCartProductRequestDto.getQuantity();
                    CartProduct cartProduct = cartRequestDto.toEntity(cart, product, quantity);
                    cartProductRepository.save(cartProduct);
                    return AddCartProductResponseDto.of(cartProduct);
                })
                .collect(Collectors.toList());
    }


    // request에서 userId를 추출할 수 있으면 user를 조회후 cart생성하고 아닐 시 그냥 cart를 생성한다.
    @Transactional
    public Long createCart(final CreateCartRequestDto cartRequestDto) {
        Long userId = cartRequestDto.getUserId();
        Cart cart;
        if (userId != null) {
            User user = userRepository.findById(userId).orElseThrow(() -> new ApiException(UserErrorCode.NO_EXIST_USER));
            cart = cartRequestDto.toEntity(user);
        } else {
            cart = cartRequestDto.toEntity();
        }
        return cartRepository.save(cart).getId();
    }


}


