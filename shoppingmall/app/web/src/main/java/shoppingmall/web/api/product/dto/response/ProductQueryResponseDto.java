package shoppingmall.web.api.product.dto.response;

import lombok.Builder;
import lombok.Getter;
import shoppingmall.domainrdb.product.domain.ProductDomain;
import shoppingmall.web.api.image.dto.response.ImageResponseDto;

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
    public ProductQueryResponseDto(Long id, String name, int price, Long categoryId, String categoryName, List<ImageResponseDto> imageResponseDtos) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.imageResponseDtos = imageResponseDtos;
    }


    public static ProductQueryResponseDto of(final ProductDomain productDomain,final List<ImageResponseDto> imageResponseDtos, final Long categoryId
    ) {
        return ProductQueryResponseDto.builder()
                .id(productDomain.getId())
                .name(productDomain.getName())
                .price(productDomain.getPrice())
                .categoryId(categoryId) // T
                .categoryName(productDomain.getCategoryDomain().getName())
                .imageResponseDtos(imageResponseDtos)
                .build();
    }

}
