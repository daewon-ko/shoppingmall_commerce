package shoppingmall.web.api.product.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import shoppingmall.common.ApiResponse;
import shoppingmall.domainrdb.image.entity.FileType;
import shoppingmall.domainrdb.product.dto.response.ProductUpdateResponseDto;
import shoppingmall.web.api.product.dto.request.ProductCreateRequestDto;
import shoppingmall.web.api.product.dto.request.ProductSearchConditionRequestDto;
import shoppingmall.web.api.product.dto.request.ProductUpdateRequestDto;
import shoppingmall.web.api.product.dto.response.ProductCreateResponseDto;
import shoppingmall.web.api.product.dto.response.ProductQueryResponseDto;
import shoppingmall.web.api.product.usecase.ProductUsecase;
import shoppingmall.web.common.argument.Login;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ProductController {
    private final ProductUsecase productUsecase;

    @PostMapping(value = "/product", consumes ={ MediaType.MULTIPART_FORM_DATA_VALUE})
    public ApiResponse<ProductCreateResponseDto> createProduct(@Login String email, @RequestPart("requestDto") @Valid ProductCreateRequestDto requestDto, @RequestPart("images") List<MultipartFile> images) {
        ProductCreateResponseDto response = productUsecase.createProduct(requestDto, images);

        return ApiResponse.ok(response);
    }

    @GetMapping("/products")
    public ApiResponse<Slice<ProductQueryResponseDto>> getAllProductList
            (@RequestParam(required = false)Long categoryId,
             @RequestParam(required = false)Integer minPrice,
             @RequestParam(required = false)Integer maxPrice,
             @RequestParam(required = false)String productName,
             @RequestParam(required = false)LocalDateTime startDate,
             @RequestParam(required = false)LocalDateTime endDate,
             @RequestParam(required = false) List<FileType> fileType,
             Pageable pageable) {

        ProductSearchConditionRequestDto searchCond = ProductSearchConditionRequestDto.builder()
                .categoryId(categoryId)
                .minPrice(minPrice)
                .maxPrice(maxPrice)
                .productName(productName)
                .startDate(startDate)
                .endDate(endDate)
                .fileTypes(fileType).build();



        Slice<ProductQueryResponseDto> result = productUsecase.getAllProductList(searchCond, pageable);
        return ApiResponse.of(HttpStatus.OK, result);
    }

    @PutMapping("/product/{id}")
    public ApiResponse<ProductUpdateResponseDto> updateProduct(@PathVariable("id") Long id,
                                                               @RequestPart("requestDto") @Valid ProductUpdateRequestDto requestDto,
                                                               @RequestPart(value = "detailImages", required = false) List<MultipartFile> detailImages,
                                                               @RequestPart(value = "thumbnailImage", required = false) MultipartFile thumbnailImage) { // 상세 이미지들


        ProductUpdateResponseDto productUpdateResponseDto = productService.updateProduct(id, requestDto, thumbnailImage, detailImages);
        return ApiResponse.of(HttpStatus.OK, productUpdateResponseDto);

    }

    // TODO : Data를 넘겨줄떄 jsonIncldude로 null을 무시한다고해도 아래와 같은 방식이 적합할까?
    @DeleteMapping("/product/{id}")
    public ApiResponse<Void> deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
        return ApiResponse.of(HttpStatus.OK, null);
    }

//    @GetMapping("/product/{id}/image")

}

