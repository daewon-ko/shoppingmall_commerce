package shoppingmall.web.api.category.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shoppingmall.common.ApiResponse;
import shoppingmall.web.api.category.dto.request.CategoryRequestDto;
import shoppingmall.web.api.category.usecase.CategoryUsecase;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryUsecase categoryUsecase;

    @PostMapping("/category")
    public ResponseEntity<ApiResponse<String>> createCategory(@RequestBody @Valid CategoryRequestDto categoryRequestDto) {
        categoryUsecase.createCategory(categoryRequestDto);
        ApiResponse<String> response = ApiResponse.of(HttpStatus.CREATED, "CREATED");
        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

}
