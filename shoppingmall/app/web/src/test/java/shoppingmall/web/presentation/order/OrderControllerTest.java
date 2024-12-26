package shoppingmall.web.presentation.order;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.http.MediaType;
import shoppingmall.domain.domain.order.dto.request.OrderCreateRequestDto;
import shoppingmall.domain.domain.order.dto.request.OrderProductCreateRequestDto;
import shoppingmall.domain.domain.order.dto.request.OrderSearchCondition;
import shoppingmall.domain.domain.order.dto.response.OrderProductResponseDto;
import shoppingmall.domain.domain.order.entity.OrderStatus;
import shoppingmall.web.presentation.support.ControllerTestSupport;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


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
    void createOrderWithCart() throws Exception {
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

    @DisplayName("페이지 사이즈 및 크기와 주문상태를 조건으로 사용자의 주문목록을 조회할 수 있다.")
    @Test
    void findPagedOrderProductsByUserIdAndStatus() throws Exception {
        //given

        // 신규생성된 주문을 1페이지부터 10개 가져온다.
        OrderProductResponseDto response1 = createOrderProductResponseDto(1L, 1L, "test", 99);
        OrderProductResponseDto response2 = createOrderProductResponseDto(2L, 2L, "test", 99);
        OrderProductResponseDto response3 = createOrderProductResponseDto(2L, 3L, "test", 100);


        Slice<OrderProductResponseDto> mockOrderList = new SliceImpl<>(List.of(response1, response2, response3));



        Mockito.when(orderService.getOrderList(
                ArgumentMatchers.eq(1L),
                        any(OrderSearchCondition.class),
                        any(Pageable.class)))
                        .thenReturn(mockOrderList);


        //when
        mockMvc.perform(get("/api/order/{userId}/list", 1L)
                        .param("orderStatus", OrderStatus.NEW.name())
                        .param("page", "1")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.content[0].orderId").value(1))
                .andExpect(jsonPath("$.data.content[0].quantity").value(99))
                .andExpect(jsonPath("$.data.content[1].orderId").value(2))
                .andExpect(jsonPath("$.data.content[1].quantity").value(99))
                .andExpect(jsonPath("$.data.content[2].orderId").value(2))
                .andExpect(jsonPath("$.data.content[2].quantity").value(100));

        //then

    }
    private static OrderProductResponseDto createOrderProductResponseDto(long orderId, long productId, String name, int quantity) {
        OrderProductResponseDto responseDto = OrderProductResponseDto.builder()
                .orderId(orderId)
                .productId(productId)
                .productName(name)
                .quantity(quantity)
                .build();
        return responseDto;
    }


    private static OrderCreateRequestDto createOrderWithCartRequest(List<OrderProductCreateRequestDto> orderProductRequests, String detailAddress, String zipCode, long cartId) {
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