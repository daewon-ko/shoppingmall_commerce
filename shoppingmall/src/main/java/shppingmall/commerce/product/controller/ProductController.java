package shppingmall.commerce.product.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import shppingmall.commerce.global.ApiResponse;
import shppingmall.commerce.product.dto.request.ProductRequestDto;
import shppingmall.commerce.product.dto.response.ProductResponseDto;
import shppingmall.commerce.product.service.ProductService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProductController {
    private final ProductService productService;

    @PostMapping("/product")
    public ApiResponse<ProductResponseDto> createProduct(@RequestPart @Valid ProductRequestDto requestDto, @RequestPart List<MultipartFile> images) {
        ProductResponseDto response = productService.createProduct(requestDto, images);

        return ApiResponse.ok(response);
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductResponseDto>> getAllProductList() {
        List<ProductResponseDto> productResponseDtoList = productService.getAllProductList();
        return ResponseEntity.status(HttpStatus.OK).body(productResponseDtoList);
    }
}
