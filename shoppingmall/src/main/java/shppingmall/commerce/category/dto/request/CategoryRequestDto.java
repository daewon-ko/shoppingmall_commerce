package shppingmall.commerce.category.dto.request;

import lombok.Getter;
import shppingmall.commerce.category.entity.Category;

import java.time.LocalDateTime;

@Getter
public class CategoryRequestDto {
    private String categoryName;

    public Category toEntity() {
        return Category.builder()
                .name(getCategoryName())
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .build();
    }
}
