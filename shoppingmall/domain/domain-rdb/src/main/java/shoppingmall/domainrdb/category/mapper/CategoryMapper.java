package shoppingmall.domainrdb.category.mapper;

import shoppingmall.domainrdb.category.CategoryDomain;
import shoppingmall.domainrdb.category.entity.Category;

public class CategoryMapper {
    public static CategoryDomain toCategoryDomain(final Category category) {
        return new CategoryDomain(category.getName());
    }
}
