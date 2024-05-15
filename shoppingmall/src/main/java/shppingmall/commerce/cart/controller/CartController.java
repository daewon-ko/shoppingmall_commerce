package shppingmall.commerce.cart.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shppingmall.commerce.cart.dto.response.AddCartProductResponseDto;
import shppingmall.commerce.cart.dto.request.AddCartRequestDto;
import shppingmall.commerce.cart.dto.request.CreateCartRequestDto;
import shppingmall.commerce.cart.service.CartService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartController {
    private final CartService cartService;


    // 장바구니 생성은 간단한 로직이기에 따로 ResponseDto 미생성
    @PostMapping
    public ResponseEntity<String> createCart(@RequestBody CreateCartRequestDto cartRequestDto) {
        cartService.createCart(cartRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("OK");
    }

    @PostMapping("/products")
    public ResponseEntity<List<AddCartProductResponseDto>> addProductToCart(@RequestBody AddCartRequestDto cartRequestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(cartService.addProductToCart(cartRequestDto));
    }
}
