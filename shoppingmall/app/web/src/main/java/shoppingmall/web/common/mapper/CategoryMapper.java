package shoppingmall.web.common.mapper;

import shoppingmall.domainrdb.category.CategoryDomain;

public abstract class CategoryMapper {
    public static CategoryDomain toCategoryDomain(String name) {
        return new CategoryDomain(name);
    }
}
