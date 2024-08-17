package shppingmall.commerce.category.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shppingmall.commerce.category.dto.request.CategoryRequestDto;
import shppingmall.commerce.category.service.CategoryService;
import shppingmall.commerce.global.ApiResponse;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    @PostMapping("/category")
    public ResponseEntity<ApiResponse<String>> createCategory(@RequestBody @Valid CategoryRequestDto categoryRequestDto) {
        categoryService.createCategory(categoryRequestDto);
        ApiResponse<String> response = ApiResponse.of(HttpStatus.CREATED, "CREATED");
        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

}
