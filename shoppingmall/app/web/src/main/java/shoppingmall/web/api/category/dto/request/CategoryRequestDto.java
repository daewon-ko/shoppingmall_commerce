package shoppingmall.web.api.category.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CategoryRequestDto {
    @NotBlank(message = "이름은 공백일 수 없습니다.")
    private String categoryName;

    @Builder
    private CategoryRequestDto(String categoryName) {
        this.categoryName = categoryName;
    }



}
