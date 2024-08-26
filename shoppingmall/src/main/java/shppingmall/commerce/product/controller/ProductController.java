package shppingmall.commerce.product.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import shppingmall.commerce.global.ApiResponse;
import shppingmall.commerce.product.dto.request.ProductUpdateRequestDto;
import shppingmall.commerce.product.dto.request.ProductCreateRequestDto;
import shppingmall.commerce.product.dto.response.ProductCreateResponseDto;
import shppingmall.commerce.product.dto.response.ProductUpdateResponseDto;
import shppingmall.commerce.product.service.ProductService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProductController {
    private final ProductService productService;

    @PostMapping("/product")
    public ApiResponse<ProductCreateResponseDto> createProduct(@RequestPart @Valid ProductCreateRequestDto requestDto, @RequestPart List<MultipartFile> images) {
        ProductCreateResponseDto response = productService.createProduct(requestDto, images);

        return ApiResponse.ok(response);
    }

    @GetMapping("/products")
    public ApiResponse<List<ProductCreateResponseDto>> getAllProductList() {
        List<ProductCreateResponseDto> productCreateResponseDtoList = productService.getAllProductList();
        return ApiResponse.ok(productCreateResponseDtoList);
    }

    @PutMapping("/product/{id}")
    public ApiResponse<ProductUpdateResponseDto> updateProduct(@PathVariable("id") Long id,
                                                               @RequestPart("requestDto") @Valid ProductUpdateRequestDto requestDto,
                                                               @RequestPart("images") List<MultipartFile> images) {

        ProductUpdateResponseDto productUpdateResponseDto = productService.updateProduct(id, requestDto, images);
        return ApiResponse.of(HttpStatus.OK, productUpdateResponseDto);

    }


}

