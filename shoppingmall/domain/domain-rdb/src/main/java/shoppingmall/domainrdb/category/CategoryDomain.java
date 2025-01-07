package shoppingmall.domainrdb.category;

import lombok.Getter;
import shoppingmall.domainrdb.category.entity.Category;

@Getter
public class CategoryDomain {
    private final String name;

    public CategoryDomain(String name) {
        this.name = name;
        validate();
    }

    public static Category toEntity(CategoryDomain categoryDomain) {
        return Category.builder()
                .name(categoryDomain.getName())
                .build();
    }

    public void validate() {
        if(name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Category name is empty");
        }
    }
}
