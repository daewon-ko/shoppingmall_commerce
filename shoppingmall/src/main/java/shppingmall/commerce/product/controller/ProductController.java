package shppingmall.commerce.product.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shppingmall.commerce.product.dto.ProductRequestDto;
import shppingmall.commerce.product.service.ProductService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProductController {
    private final ProductService productService;

    @PostMapping("/product")
    public ResponseEntity<String> createProduct(@ModelAttribute ProductRequestDto requestDto) {
        productService.createProduct(requestDto);
        return ResponseEntity.status(HttpStatus.OK).body("OK");
    }


}
