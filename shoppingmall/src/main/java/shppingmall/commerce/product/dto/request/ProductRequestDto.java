package shppingmall.commerce.product.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
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
    @NotBlank(message = "이름은 공백이 될 수 없습니다.")
    private String name;
    @PositiveOrZero(message = "가격은 음수가 될 수 없습니다.")
    @Max(value =9999999999L, message = "가격은 9999999999 이상일 수 없습니다.")
    private int price;

    @NotNull(message = "카테고리 번호를 입력해주세요.")
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
