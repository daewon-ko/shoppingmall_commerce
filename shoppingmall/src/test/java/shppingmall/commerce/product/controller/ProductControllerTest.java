package shppingmall.commerce.product.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import shppingmall.commerce.ControllerTestSupport;
import shppingmall.commerce.product.dto.request.ProductRequestDto;
import shppingmall.commerce.product.dto.response.ProductResponseDto;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


class ProductControllerTest extends ControllerTestSupport {

    @Mock
    private MockMultipartFile multipartFile;

    @DisplayName("신규 상품을 생성한다.")
    @Test
    void createProduct() throws Exception {
        //given
        ProductRequestDto request = ProductRequestDto.builder()
                .name("test")
                .price(10000)
                .sellerId(1L)
                .categoryId(1L)
                .build();

        multipartFile = new MockMultipartFile("images", "test-image.jpg", "image/jpeg", "image.png".getBytes());
        MockMultipartFile mockRequest = new MockMultipartFile("requestDto", null, "application/json", objectMapper.writeValueAsString(request).getBytes(StandardCharsets.UTF_8));


        // when then
        mockMvc.perform((multipart("/api/product")
                        .file(multipartFile)
                        .file(mockRequest)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.httpStatus").value("OK"))
                .andExpect(jsonPath("$.message").value("OK"));


    }

    @DisplayName("신규 상품을 생성할때, 상품 이름은 필수값이다.")
    @Test
    void createProductExceptName() throws Exception {
        //given
        ProductRequestDto request = ProductRequestDto.builder()
                .price(10000)
                .sellerId(1L)
                .categoryId(1L)
                .build();

        multipartFile = new MockMultipartFile("images", "test-image.jpg", "image/jpeg", "image.png".getBytes());
        MockMultipartFile mockRequest = new MockMultipartFile("requestDto", null, "application/json", objectMapper.writeValueAsString(request).getBytes(StandardCharsets.UTF_8));


        // when then
        mockMvc.perform((multipart("/api/product")
                        .file(multipartFile)
                        .file(mockRequest)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.httpStatus").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("이름은 공백이 될 수 없습니다."));


    }


    @DisplayName("신규 상품을 생성할때, 판매자 id는 null이 될 수 없다.")
    @Test
    void createProductExceptSellerId() throws Exception {
        //given
        ProductRequestDto request = ProductRequestDto.builder()
                .name("test")
                .sellerId(null)
                .price(10000)
                .categoryId(1L)
                .build();

        multipartFile = new MockMultipartFile("images", "test-image.jpg", "image/jpeg", "image.png".getBytes());
        MockMultipartFile mockRequest = new MockMultipartFile("requestDto", null, "application/json", objectMapper.writeValueAsString(request).getBytes(StandardCharsets.UTF_8));


        // when then
        mockMvc.perform((multipart("/api/product")
                        .file(multipartFile)
                        .file(mockRequest)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.httpStatus").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("판매자의 id를 입력해주세요."));


    }


    @DisplayName("신규 상품을 생성할때, 상품 가격은 음수가 될 수 없다.")
    @Test
    void createProductWithZeroPrice() throws Exception {
        //given
        ProductRequestDto request = ProductRequestDto.builder()
                .name("test")
                .sellerId(1L)
                .price(-1)
                .categoryId(1L)
                .build();

        multipartFile = new MockMultipartFile("images", "test-image.jpg", "image/jpeg", "image.png".getBytes());
        MockMultipartFile mockRequest = new MockMultipartFile("requestDto", null, "application/json", objectMapper.writeValueAsString(request).getBytes(StandardCharsets.UTF_8));


        // when then
        mockMvc.perform((multipart("/api/product")
                        .file(multipartFile)
                        .file(mockRequest)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.httpStatus").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("가격은 음수가 될 수 없습니다."));


    }

    @DisplayName("전체 상품을 조회한다.")
    @Test
    void getAllProducts() throws Exception {

        //given


        ProductResponseDto response = createProductResponse(1L, "test", 1L, "test", 10000, List.of(1L, 2L, 3L));
        Mockito.when(productService.getAllProductList())
                .thenReturn(List.of(response));

        //when


        mockMvc.perform(get(
                        "/api/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.httpStatus").value("OK"))
                .andExpect(jsonPath("$.data[0].id").value(response.getId()))
                .andExpect(jsonPath("$.data[0].name").value(response.getName()))
                .andExpect(jsonPath("$.data[0].price").value(response.getPrice()));

        //then

    }

    private static ProductResponseDto createProductResponse(long id, String name, long categoryId, String categoryName, int price, List<Long> imageIds) {
        ProductResponseDto response = ProductResponseDto.builder()
                .id(id)
                .name(name)
                .categoryId(categoryId)
                .categoryName(categoryName)
                .price(price)
                .imageIds(imageIds)
                .build();
        return response;
    }


}