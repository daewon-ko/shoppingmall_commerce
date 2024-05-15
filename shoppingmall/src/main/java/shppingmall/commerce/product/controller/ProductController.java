package shppingmall.commerce.product.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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
    public ResponseEntity<String> createProduct(@RequestPart ProductRequestDto requestDto, @RequestPart List<MultipartFile> images) {
        productService.createProduct(requestDto, images);
        return ResponseEntity.status(HttpStatus.OK).body("OK");
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductResponseDto>> getAllProductList() {
        List<ProductResponseDto> productResponseDtoList = productService.getAllProductList();
        return ResponseEntity.status(HttpStatus.OK).body(productResponseDtoList);
    }
}
