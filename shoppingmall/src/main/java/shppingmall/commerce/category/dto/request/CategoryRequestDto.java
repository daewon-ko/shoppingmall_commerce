package shppingmall.commerce.category.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import shppingmall.commerce.category.entity.Category;


@Getter
public class CategoryRequestDto {
    @NotBlank(message = "이름은 공백일 수 없습니다.")
    private String categoryName;

    @Builder
    private CategoryRequestDto(String categoryName) {
        this.categoryName = categoryName;
    }

    public Category toEntity() {
        return Category.builder()
                .name(getCategoryName())
                .build();
    }
}
