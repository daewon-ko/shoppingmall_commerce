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

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    @PostMapping("/category")
    public ResponseEntity<String> createCategory(@RequestBody @Valid CategoryRequestDto categoryRequestDto) {
        categoryService.createCategory(categoryRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("OK");

    }

}
