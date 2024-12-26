package shoppingmall.web.presentation.cart;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shoppingmall.common.ApiResponse;
import shoppingmall.domain.domain.cart.dto.dto.request.AddCartRequestDto;
import shoppingmall.domain.domain.cart.dto.dto.request.CreateCartRequestDto;
import shoppingmall.domain.domain.cart.dto.dto.response.AddCartProductResponseDto;
import shoppingmall.domain.domain.cart.service.CartService;


import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cart")
public class CartController {
    private final CartService cartService;


    // 장바구니 생성은 간단한 로직이기에 따로 ResponseDto 미생성
    @PostMapping
    public ResponseEntity<ApiResponse<String>> createCart(@RequestBody @Valid CreateCartRequestDto cartRequestDto) {
        cartService.createCart(cartRequestDto);
        return ResponseEntity.ok(ApiResponse.of(HttpStatus.CREATED, "OK"));
    }

    @PostMapping("/products")
    public ResponseEntity<ApiResponse<List<AddCartProductResponseDto>>> addProductToCart(@RequestBody @Valid AddCartRequestDto cartRequestDto) {
        return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK,cartService.addProductToCart(cartRequestDto)));
    }
}
