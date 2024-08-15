package shppingmall.commerce.product.dto.response;

import lombok.Builder;
import lombok.Getter;
import shppingmall.commerce.product.entity.Product;

@Getter
@Builder
public class ProductResponseDto {
    private Long id;
    private String name;
    private int price;
    private Long categoryId;
    private String categoryName;


    @Builder
    private ProductResponseDto(Long id, String name, int price, Long categoryId, String categoryName) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public static ProductResponseDto of(Product product, Long categoryId) {
        return ProductResponseDto.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .categoryId(categoryId)
                .build();

    }
}
