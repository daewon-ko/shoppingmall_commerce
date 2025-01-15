package shoppingmall.domainrdb.product.domain;

import lombok.Builder;
import lombok.Getter;
import shoppingmall.domainrdb.category.CategoryDomain;
import shoppingmall.domainrdb.category.service.CategoryId;
import shoppingmall.domainrdb.user.UserDomain;
import shoppingmall.domainrdb.user.UserId;

@Getter
public class ProductDomain {
    private final ProductId productId;
    private final String name;
    private final Integer price;
    private final CategoryId categoryId;
    private final UserId userId;

    public ProductDomain(ProductId productId, String name, Integer price, CategoryId categoryId, UserId userId) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.categoryId = categoryId;
        this.userId = userId;
    }

    // 쓰기 작업을 위한 팩토리 메서드
    public static ProductDomain createForWrite(String name, int price, CategoryId categoryId, UserId userId) {
        return new ProductDomain(null, name, price, categoryId, userId);
    }

    // 읽기 작업을 위한 팩토리 메서드
    public static ProductDomain createForRead(Long id, String name, int price, CategoryId categoryId, UserId userId) {
        return new ProductDomain(ProductId.from(id), name, price, categoryId, userId);
    }


    private void validate() {
        validateName();
        validatePrice();
    }


    private void validateName() {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Product name is empty");
        }
    }

    private void validatePrice() {
        if (price < 0) {
            throw new IllegalArgumentException("Price is negative");
        }
        if (price > 9999999999L) {
            throw new IllegalArgumentException("Price is over 9999999999");
        }
    }
}
