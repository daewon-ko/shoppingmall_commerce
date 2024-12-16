package shoppingmall.core.domain.product.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ProductUpdateResponseDto {

    private Long productId;
    private String name;
    private int price;
    private List<Long> images;

    @Builder
    private ProductUpdateResponseDto(Long productId, String name, int price, List<Long> images) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.images = images;
    }
}
