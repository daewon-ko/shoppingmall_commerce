package shppingmall.commerce.category.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.hibernate.validator.constraints.Range;
import shppingmall.commerce.category.entity.Category;

import java.time.LocalDateTime;

@Getter
public class CategoryRequestDto {
    @NotBlank(message = "이름은 공백일 수 없습니다.")
    private String categoryName;

    public Category toEntity() {
        return Category.builder()
                .name(getCategoryName())
                .build();
    }
}
