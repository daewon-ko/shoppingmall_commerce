package shoppingmall.web.common.mapper;

import shoppingmall.domainrdb.category.CategoryDomain;

public abstract class CategoryDtoMapper {
    public static CategoryDomain toCategoryDomain(String name) {
        return CategoryDomain.createForWrite(name);
    }
}
