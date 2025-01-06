package shoppingmall.domainrdb.mapper;

import shoppingmall.domainrdb.product.ProductDomain;
import shoppingmall.domainrdb.product.entity.Product;

public class ProductEntityMapper {

    public static Product createProductEntity(final ProductDomain productDomain) {
        return Product.builder()
                .name(productDomain.getName())
                .price(productDomain.getPrice())
                .category(CategoryEntityMapper.toEntity(productDomain.getCategoryDomain()))
                .seller(UserEntityMapper.toEntity(productDomain.getUserDomain())).build();
    }
}
