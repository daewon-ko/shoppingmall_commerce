package shppingmall.commerce.cart.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shppingmall.commerce.cart.dto.CreateCartRequestDto;
import shppingmall.commerce.cart.entity.Cart;
import shppingmall.commerce.cart.repository.CartRepository;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;



    public void addProductToCart(final CreateCartRequestDto cartRequestDto) {

    }


    // Cart를 생성한다.
    public void createCart(final CreateCartRequestDto cartRequestDto) {
        Cart cart = cartRequestDto.toEntity();
        cartRepository.save(cart);
    }
}
