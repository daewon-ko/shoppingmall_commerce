package shoppingmall.domainservice.domain.product.dto.response;

import lombok.Builder;
import lombok.Getter;
import shoppingmall.domainrdb.product.domain.ProductDomain;
import shoppingmall.domainservice.domain.image.dto.response.ImageResponseDto;

import java.util.List;

@Getter
public class ProductQueryResponseDto {
    private Long id;
    private String name;
    private int price;
    private Long categoryId;
    private List<ImageResponseDto> imageResponseDtos;

    @Builder
    public ProductQueryResponseDto(Long id, String name, int price, Long categoryId, List<ImageResponseDto> imageResponseDtos) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.categoryId = categoryId;
        this.imageResponseDtos = imageResponseDtos;
    }


    public static ProductQueryResponseDto of(final ProductDomain productDomain, final List<ImageResponseDto> imageResponseDto) {
        return ProductQueryResponseDto.builder()
                .id(productDomain.getProductId().getValue())
                .name(productDomain.getName())
                .price(productDomain.getPrice())
                .categoryId(productDomain.getCategoryId().getValue())
                .imageResponseDtos(imageResponseDto)
                .build();
    }

}
