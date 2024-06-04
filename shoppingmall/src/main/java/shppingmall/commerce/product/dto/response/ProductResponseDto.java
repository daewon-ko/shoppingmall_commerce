package shppingmall.commerce.product.dto.response;

import lombok.Builder;
import lombok.Getter;
@Getter
@Builder
public class ProductResponseDto {
    private Long id;
    private String name;
    private int price;
    private String imageUrl;
    private Long categoryId;
    private String categoryName;



}
