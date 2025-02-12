package shoppingmall.domainrdb.mapper;

import shoppingmall.domainrdb.category.entity.Category;
import shoppingmall.domainrdb.category.mapper.CategoryMapper;
import shoppingmall.domainrdb.category.service.CategoryId;
import shoppingmall.domainrdb.product.domain.ProductDomain;
import shoppingmall.domainrdb.product.entity.Product;
import shoppingmall.domainrdb.user.UserId;
import shoppingmall.domainrdb.user.entity.User;

public class ProductEntityMapper {

    public static Product createProductEntity(final ProductDomain productDomain) {

        CategoryId categoryId = productDomain.getCategoryId();
        UserId userId = productDomain.getUserId();

        Category category = Category.fromCategoryId(categoryId);
        User user = User.fromUserId(userId);

        return Product.builder()
                .name(productDomain.getName())
                .price(productDomain.getPrice())
                .category(category)
                .seller(user).build();
    }

//    public static Slice<ProductQueryResponse> createProductSearchDetails(final ProductQueryResponse)

    // TODO : N+1 생길 가능성 존재
    // Seller도 같이 조회해야 N+1 문제 해결 가능
    public static ProductDomain toProductDomain(final Product product) {

        return ProductDomain.createForRead(product.getId(), product.getName(), product.getPrice(), CategoryId.from(product.getCategory().getId()), UserId.from(product.getSeller().getId()));


    }

    public static Product toProductEntity(final ProductDomain productDomain) {

        Category category = Category.fromCategoryId(productDomain.getCategoryId());
        User user = User.fromUserId(productDomain.getUserId());


        return Product.builder()
                .id(productDomain.getProductId().getValue())
                .name(productDomain.getName())
                .price(productDomain.getPrice())
                .category(category)
                .seller(user)
                .build();
    }


}
