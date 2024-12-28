package shoppingmall.domain.domain.product.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Getter;
import shoppingmall.domain.domain.category.entity.Category;
import shoppingmall.domain.domain.product.entity.Product;
import shoppingmall.domain.domain.user.entity.User;

/**
 * 상품을 생성을 요청하는 DTO입니다.
 */
@Getter
@Builder
public class ProductCreateRequestDto {
    @NotBlank(message = "이름은 공백이 될 수 없습니다.")
    private String name;
    @PositiveOrZero(message = "가격은 음수가 될 수 없습니다.")
    @Max(value = 9999999999L, message = "가격은 9999999999 이상일 수 없습니다.")
    private int price;

    @NotNull(message = "카테고리 번호를 입력해주세요.")
    private Long categoryId;

    @NotNull(message = "판매자의 id를 입력해주세요.")
    private Long sellerId;


    public Product toEntity(final Category category, User user) {
        return Product.builder()
                .name(getName())
                .price(getPrice())
                .category(category)
                .seller(user)
                .build();
    }
}
