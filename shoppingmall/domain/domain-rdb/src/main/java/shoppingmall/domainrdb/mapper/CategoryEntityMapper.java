package shoppingmall.domainrdb.mapper;

import shoppingmall.domainrdb.category.CategoryDomain;
import shoppingmall.domainrdb.category.entity.Category;

public class CategoryEntityMapper {

    public static Category toEntity(CategoryDomain categoryDomain) {
        return Category.builder()
                .name(categoryDomain.getName())
                .build();
    }

}
