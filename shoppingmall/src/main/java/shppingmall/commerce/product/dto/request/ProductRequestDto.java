package shppingmall.commerce.product.dto.request;

import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;
import shppingmall.commerce.category.entity.Category;
import shppingmall.commerce.product.entity.Product;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 상품을 생성을 요청하는 DTO입니다.
 */
@Getter
@Builder
public class ProductRequestDto {
    private String name;
    private int price;
    private Long categoryId;


    public Product toEntity(final String fullPathUrl, final Category category) {
        return Product.builder()
                .name(getName())
                .price(getPrice())
                .category(category)
                .imageUrl(fullPathUrl)
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .build();
    }
}
