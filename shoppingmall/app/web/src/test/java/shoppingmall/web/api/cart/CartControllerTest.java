package shoppingmall.web.api.cart;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import shoppingmall.web.api.cart.dto.request.CreateCartRequestDto;
import shoppingmall.web.api.support.ControllerTestSupport;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CartControllerTest extends ControllerTestSupport {


    @DisplayName("장바구니를 생성한다.")
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
        ).andExpect(status().isOk());


    }

    @DisplayName("사용자 Id 없이 장바구니 생성은 불가능하다.")
    @Test
    void createCartWithOutUserId() throws Exception {
        //given
        CreateCartRequestDto requestDto = CreateCartRequestDto.builder()
                .build();

        Mockito.when(cartService.createCart(any(CreateCartRequestDto.class)))
                .thenReturn(1L);

        //when, then
        mockMvc.perform(post("/api/cart")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto))
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("사용자 ID를 반드시 입력해주세요."));


    }


    @DisplayName("장바구니에 상품을 추가한다.")
    @Test
    void addCart() throws Exception {

        //given
        AddCartProductRequestDto addCartProductRequest1 = createAddCartProductRequest(1L, 10);

        AddCartProductRequestDto addCartProductRequest2 = createAddCartProductRequest(2L, 10);


        AddCartRequestDto addCartRequest = createAddCartRequest(addCartProductRequest1, addCartProductRequest2, 1L);

        AddCartProductResponseDto addCartProductResponse1 = createAddCartResponse(1L, 10);
        AddCartProductResponseDto addCartProductResponse2 = createAddCartResponse(2L, 10);

        Mockito.when(cartService.addProductToCart(any(AddCartRequestDto.class)))
                .thenReturn(List.of(addCartProductResponse1, addCartProductResponse2));


        //when, then
        mockMvc.perform(post("/api/cart/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addCartRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.data", hasSize(2)))
                .andExpect(jsonPath("$.data[0].cartProductId").value(1))
                .andExpect(jsonPath("$.data[0].quantity").value(10))
                .andExpect(jsonPath("$.data[1].cartProductId").value(2))
                .andExpect(jsonPath("$.data[1].quantity").value(10));


    }

    private static AddCartProductResponseDto createAddCartResponse(long cartProductId, int quantity) {
        AddCartProductResponseDto addCartProductResponse1 = AddCartProductResponseDto.builder()
                .cartProductId(cartProductId)
                .quantity(quantity)
                .build();
        return addCartProductResponse1;
    }

    private static AddCartRequestDto createAddCartRequest(AddCartProductRequestDto addCartProductRequest1, AddCartProductRequestDto addCartProductRequest2, long cartId) {
        AddCartRequestDto addCartRequest = AddCartRequestDto.builder()
                .cartId(cartId)
                .cartProductRequestDtoList(List.of(addCartProductRequest1, addCartProductRequest2))
                .build();
        return addCartRequest;
    }

    private static AddCartProductRequestDto createAddCartProductRequest(long productId, int quantity) {
        AddCartProductRequestDto addCartProductRequest1 = AddCartProductRequestDto.builder()
                .productId(productId)
                .quantity(quantity).build();
        return addCartProductRequest1;
    }


}