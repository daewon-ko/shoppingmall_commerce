package shppingmall.commerce.cart.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shppingmall.commerce.cart.dto.request.AddCartProductRequestDto;
import shppingmall.commerce.cart.dto.response.AddCartProductResponseDto;
import shppingmall.commerce.cart.dto.request.AddCartRequestDto;
import shppingmall.commerce.cart.dto.request.CreateCartRequestDto;
import shppingmall.commerce.cart.entity.Cart;
import shppingmall.commerce.cart.entity.CartProduct;
import shppingmall.commerce.cart.repository.CartProductRepository;
import shppingmall.commerce.cart.repository.CartRepository;
import shppingmall.commerce.product.entity.Product;
import shppingmall.commerce.product.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final CartProductRepository cartProductRepository;
    private final ProductRepository productRepository;



    //Cart에 상품을 추가한다.
    @Transactional
    public List<AddCartProductResponseDto> addProductToCart(final AddCartRequestDto cartRequestDto) {
        // TODO : 예외정의 필요
        // TODO : Stream 이용
        Cart cart = cartRepository.findById(cartRequestDto.getCartId()).orElseThrow(() -> new IllegalArgumentException("해당하는 카트가 존재하지 않습니다."));
        List<AddCartProductRequestDto> cartProductRequestDtoList = cartRequestDto.getCartProductRequestDtoList();
        List<AddCartProductResponseDto> cartProductResponseDtoList = new ArrayList<>();

        for (AddCartProductRequestDto addCartProductRequestDto : cartProductRequestDtoList) {
            Product product = productRepository.findById(addCartProductRequestDto.getProductId()).orElseThrow(() -> new IllegalArgumentException("해당 상품은 존재하지 않습니다."));
            int quantity = addCartProductRequestDto.getQuantity();
            CartProduct cartProduct = cartRequestDto.toEntity(cart, product, quantity);
            cartProductRepository.save(cartProduct);
            cartProductResponseDtoList.add(AddCartProductResponseDto.of(cartProduct));

        }
        return cartProductResponseDtoList;
    }


    // Cart를 생성한다.
    @Transactional
    public void createCart(final CreateCartRequestDto cartRequestDto) {
        Cart cart = cartRequestDto.toEntity();
        cartRepository.save(cart);
    }
}
