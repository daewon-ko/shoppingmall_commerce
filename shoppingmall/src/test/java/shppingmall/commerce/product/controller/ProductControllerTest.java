package shppingmall.commerce.product.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import shppingmall.commerce.ControllerTestSupport;
import shppingmall.commerce.product.dto.request.ProductCreateRequestDto;
import shppingmall.commerce.product.dto.request.ProductUpdateRequestDto;
import shppingmall.commerce.product.dto.response.ProductCreateResponseDto;
import shppingmall.commerce.product.dto.response.ProductUpdateResponseDto;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


class ProductControllerTest extends ControllerTestSupport {

    @Mock
    private MockMultipartFile multipartFile;

    @DisplayName("신규 상품을 생성한다.")
    @Test
    void createProduct() throws Exception {
        //given
        ProductCreateRequestDto request = ProductCreateRequestDto.builder()
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
        ProductCreateRequestDto request = ProductCreateRequestDto.builder()
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
        ProductCreateRequestDto request = ProductCreateRequestDto.builder()
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
        ProductCreateRequestDto request = ProductCreateRequestDto.builder()
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


        ProductCreateResponseDto response = createProductResponse(1L, "test", 1L, "test", 10000, List.of(1L, 2L, 3L));
        Mockito.when(productService.getAllProductList())
                .thenReturn(List.of(response));

        //when, then


        mockMvc.perform(get(
                        "/api/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.httpStatus").value("OK"))
                .andExpect(jsonPath("$.data[0].id").value(response.getId()))
                .andExpect(jsonPath("$.data[0].name").value(response.getName()))
                .andExpect(jsonPath("$.data[0].price").value(response.getPrice()));

    }

    @DisplayName("상품을 수정할 수 있다.")
    @Test
    void updateProduct() throws Exception {
        //given
        Long productId = 1L;
        MockMultipartFile mockMultipartFile1 = new MockMultipartFile("images", "test.jpg", "image/jpeg", "image.png".getBytes());

        ProductUpdateRequestDto updateRequestDto = ProductUpdateRequestDto.builder()
                .name("test")
                .price(10000)
                .imagesToDelete(List.of(1L))
                .build();

        ProductUpdateResponseDto updateResponseDto = ProductUpdateResponseDto.builder()
                .productId(1L)
                .name("updated Product")
                .price(10000)
                .images(List.of(2L, 3L))
                .build();

        Mockito.when(productService.updateProduct(Mockito.eq(productId), Mockito.any(ProductUpdateRequestDto.class), Mockito.anyList()))
                .thenReturn(updateResponseDto);


        String updateRequestJson = objectMapper.writeValueAsString(updateRequestDto);

        MockMultipartFile mockMultipartFile2 = new MockMultipartFile("requestDto", "test", "application/json", updateRequestJson.getBytes(StandardCharsets.UTF_8));

        //when, then
        mockMvc.perform((multipart(HttpMethod.PUT, "/api/product/{id}", productId)
                        .file(mockMultipartFile1)
                        .file(mockMultipartFile2)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .accept(MediaType.APPLICATION_JSON)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.httpStatus").value("OK"))
                .andExpect(jsonPath("$.data.name").value(updateResponseDto.getName()))
                .andExpect(jsonPath("$.data.productId").value(updateResponseDto.getProductId()))
                .andExpect(jsonPath("$.data.images[0]").value(2L))
                .andExpect(jsonPath("$.data.images[1]").value(3L));


    }


    private static ProductCreateResponseDto createProductResponse(long id, String name, long categoryId, String categoryName, int price, List<Long> imageIds) {
        ProductCreateResponseDto response = ProductCreateResponseDto.builder()
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