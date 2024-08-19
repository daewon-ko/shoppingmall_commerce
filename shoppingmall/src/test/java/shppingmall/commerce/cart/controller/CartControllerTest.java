package shppingmall.commerce.cart.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import shppingmall.commerce.ControllerTestSupport;
import shppingmall.commerce.cart.dto.request.AddCartRequestDto;
import shppingmall.commerce.cart.dto.request.CreateCartRequestDto;
import shppingmall.commerce.cart.dto.response.AddCartProductResponseDto;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CartControllerTest extends ControllerTestSupport {


    @DisplayName("")
    @Test
    void createCart() throws Exception {
        //given
        CreateCartRequestDto requestDto = CreateCartRequestDto.builder()
                .userId(1L)
                .build();

        Mockito.when(cartService.createCart(any(CreateCartRequestDto.class)))
                .thenReturn(1L);

        //when, then
        mockMvc.perform(post("/api/cart")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto))
        ).andExpect(status().isCreated());


    }


    @DisplayName("")
    @Test
    void addCart() {

        //given


        AddCartProductResponseDto addCartProductResponse1 = AddCartProductResponseDto.builder()
                .cartProductId(1L)
                .quantity(10)
                .build();

        AddCartProductResponseDto addCartProductResponse2 = AddCartProductResponseDto.builder()
                .cartProductId(2L)
                .quantity(10)
                .build();

        Mockito.when(cartService.addProductToCart(any(AddCartRequestDto.class)))
                .thenReturn(List.of(addCartProductResponse1, addCartProductResponse2));


        //when, then



    }


}