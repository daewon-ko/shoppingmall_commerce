package shppingmall.commerce.order.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import shppingmall.commerce.ControllerTestSupport;
import shppingmall.commerce.order.dto.request.OrderCreateRequestDto;
import shppingmall.commerce.order.dto.request.OrderProductCreateRequestDto;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


class OrderControllerTest extends ControllerTestSupport {


    @DisplayName("장바구니 생성 없이, 주문을 할 수 있다.")
    @Test
    void createDirectOrder() throws Exception {
        //given
        OrderProductCreateRequestDto orderProductRequest1 = createOrderProductRequest(1L, 100);
        OrderProductCreateRequestDto orderProductRequest2 = createOrderProductRequest(2L, 100);

        OrderCreateRequestDto orderCreateRequest = createOrderWithoutCartRequest(List.of(orderProductRequest1, orderProductRequest2), "test", "test", 1L);

        //when, then
        mockMvc.perform
                        (post("/api/order")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(orderCreateRequest))
                        ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.httpStatus").value("OK"));

    }


    @DisplayName("주소를 생략하면, 주문을 할 수 없다.")
    @Test
    void createOrderWithOutZipCode() throws Exception {
        //given
        OrderProductCreateRequestDto orderProductRequest1 = createOrderProductRequest(1L, 100);
        OrderProductCreateRequestDto orderProductRequest2 = createOrderProductRequest(2L, 100);

        OrderCreateRequestDto orderCreateRequest = createOrderWithoutCartRequest(List.of(orderProductRequest1, orderProductRequest2), "test", "", 1L);  //zipcode를 공백으로 입력

        //when, then
        mockMvc.perform
                        (post("/api/order")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(orderCreateRequest))
                        ).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.httpStatus").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("주소가 입력되지 않았습니다."));
    }


    @DisplayName("장바구니를 지정하고, 주문을 할 수 있다.")
    @Test
    void createOrderWithCart() throws Exception{
        //given
        OrderProductCreateRequestDto orderProductRequest1 = createOrderProductRequest(1L, 100);
        OrderProductCreateRequestDto orderProductRequest2 = createOrderProductRequest(2L, 100);


        OrderCreateRequestDto orderCreateWithCartRequest = createOrderWithCartRequest(List.of(orderProductRequest1, orderProductRequest2), "test", "test", 1L);


        //when
        mockMvc.perform(post("/api/order")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orderCreateWithCartRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.httpStatus").value("OK"));
        //then

    }


    private static OrderCreateRequestDto createOrderWithCartRequest( List<OrderProductCreateRequestDto> orderProductRequests, String detailAddress, String zipCode, long cartId) {
        OrderCreateRequestDto orderCreateRequest = OrderCreateRequestDto.builder()
                .cartId(cartId)
                .detailAddress(detailAddress)
                .zipCode(zipCode)
                .orderProductRequestDtoList(orderProductRequests)
                .build();
        return orderCreateRequest;
    }




    private static OrderCreateRequestDto createOrderWithoutCartRequest(List<OrderProductCreateRequestDto> orderProductRequests, String detailAddress, String zipCode, long cartId) {
        OrderCreateRequestDto orderCreateRequest = OrderCreateRequestDto.builder()
                .cartId(null)
                .detailAddress(detailAddress)
                .zipCode(zipCode)
                .orderProductRequestDtoList(orderProductRequests)
                .build();
        return orderCreateRequest;
    }


    private static OrderProductCreateRequestDto createOrderProductRequest(long productId, int quantity) {
        OrderProductCreateRequestDto orderProductRequest2 = OrderProductCreateRequestDto.builder()
                .productId(productId)
                .quantity(quantity)
                .build();
        return orderProductRequest2;
    }


}