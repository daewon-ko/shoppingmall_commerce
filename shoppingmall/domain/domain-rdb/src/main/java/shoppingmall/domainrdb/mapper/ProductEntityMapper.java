package shoppingmall.domainrdb.mapper;

import shoppingmall.domainrdb.category.mapper.CategoryMapper;
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

//    public static Slice<ProductQueryResponse> createProductSearchDetails(final ProductQueryResponse)

    public static ProductDomain toProductDomain(final Product product) {

        return ProductDomain.builder()
                .id(product.getId())
                .price(product.getPrice())
                .name(product.getName())
                .categoryDomain(CategoryMapper.toCategoryDomain(product.getCategory()))
                .build();
//                .userDomain(product.getSeller())  // UserDomain으로 mapping필요

    }


}
