package shoppingmall.core.domain.product.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import shoppingmall.core.domain.image.dto.response.ImageResponseDto;
import shoppingmall.core.domain.product.entity.Product;

import java.util.List;

@Getter
public class ProductQueryResponseDto {
    private Long id;
    private String name;
    private int price;
    private Long categoryId;
    private String categoryName;
    private List<ImageResponseDto> imageResponseDtos;

    @Builder
    @QueryProjection
    public ProductQueryResponseDto(Long id, String name, int price, Long categoryId, String categoryName, List<ImageResponseDto> imageResponseDtos) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.imageResponseDtos = imageResponseDtos;
    }


    public static ProductQueryResponseDto of(Product product, List<ImageResponseDto> imageResponseDtos) {
        return ProductQueryResponseDto.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .categoryId(product.getCategory().getId())
                .categoryName(product.getCategory().getName())
                .imageResponseDtos(imageResponseDtos)
                .build();
    }
}
