package shoppingmall.core.domain.product.dto.response;

import lombok.Builder;
import lombok.Getter;
import shoppingmall.core.domain.product.entity.Product;

import java.util.List;

@Getter
public class ProductCreateResponseDto {
    private Long id;
    private String name;
    private int price;
    private Long categoryId;
    private String categoryName;
    private List<Long> imageIds;

    @Builder
    private ProductCreateResponseDto(Long id, String name, int price, Long categoryId, String categoryName, List<Long> imageIds) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.imageIds = imageIds;
    }



    public static ProductCreateResponseDto of(Product product, Long categoryId, List<Long> imageIds) {
        return ProductCreateResponseDto.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .categoryId(categoryId)
                .categoryName(product.getCategory().getName())
                .imageIds(imageIds)
                .build();

    }
}
