package shppingmall.commerce.cart.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shppingmall.commerce.cart.dto.AddCartProductRequestDto;
import shppingmall.commerce.cart.dto.AddCartRequestDto;
import shppingmall.commerce.cart.dto.CreateCartRequestDto;
import shppingmall.commerce.cart.service.CartService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartController {
    private final CartService cartService;


    @PostMapping
    public ResponseEntity<String> createCart(@RequestBody CreateCartRequestDto cartRequestDto) {
        cartService.createCart(cartRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("OK");
    }

    @PostMapping("/products")
    public ResponseEntity<String> addProductToCart(@RequestBody AddCartRequestDto cartRequestDto) {

        cartService.addProductToCart(cartRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body("OK");
    }
}
