package shoppingmall.domainrdb.category;

import lombok.Getter;
import shoppingmall.domainrdb.category.entity.Category;
import shoppingmall.domainrdb.category.service.CategoryId;

@Getter
public class CategoryDomain {
    private CategoryId categoryId;
    private final String name;

    private CategoryDomain(CategoryId categoryId, String name) {
        this.categoryId = categoryId;
        this.name = name;
    }

    public static CategoryDomain createForWrite(String name) {
        return new CategoryDomain(null, name);
    }

    public static CategoryDomain createForRead(Long id, String name) {
        return new CategoryDomain(CategoryId.from(id), name);
    }

    public void validate() {
        if(name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Category name is empty");
        }
    }
}
