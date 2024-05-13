package shppingmall.commerce.category.dto;

import lombok.Getter;
import shppingmall.commerce.category.entity.Category;

import java.time.LocalDateTime;

@Getter
public class CategoryRequestDto {
    private String categoryName;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public Category toEntity() {
        return Category.builder()
                .name(getCategoryName())
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .build();
    }
}
