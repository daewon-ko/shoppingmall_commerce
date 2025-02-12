package shoppingmall.domainservice.domain.product.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ProductCreateResponseDto {
    private Long id;
    private String name;
    private int price;
    private String categoryName;
    private List<Long> imageIds;

    @Builder
    private ProductCreateResponseDto(Long id, String name, int price,  String categoryName, List<Long> imageIds) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.categoryName = categoryName;
        this.imageIds = imageIds;
    }




}
