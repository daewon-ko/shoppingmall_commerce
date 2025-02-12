package shoppingmall.domainservice.domain.product.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductUpdateResponseDto {

    private Long productId;
    private String name;
    private int price;


    @Builder
    private ProductUpdateResponseDto(Long productId, String name, int price) {
        this.productId = productId;
        this.name = name;
        this.price = price;

    }
}
