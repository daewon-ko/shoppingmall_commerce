package shoppingmall.domainservice.domain.product.dto.request;

import lombok.Builder;
import lombok.Getter;
import shoppingmall.domainrdb.category.entity.Category;
import shoppingmall.domainrdb.product.entity.Product;
import shoppingmall.domainrdb.user.entity.User;

/**
 * 상품을 생성을 요청하는 DTO입니다.
 */
@Builder
@Getter
public class ProductCreateRequestDto {
    private String name;
    private int price;

    private String cagegoryName;

    private String sellerEmail;


    public Product toEntity(final Category category, User user) {
        return Product.builder()
                .name(getName())
                .price(getPrice())
                .category(category)
                .seller(user)
                .build();
    }
}
