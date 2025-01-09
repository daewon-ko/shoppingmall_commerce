package shoppingmall.domainrdb.product;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Builder;
import lombok.Getter;
import shoppingmall.domainrdb.category.CategoryDomain;
import shoppingmall.domainrdb.user.UserDomain;

@Getter
public class ProductDomain {
    private final Long id;
    private final String name;
    private final Integer price;
    private final CategoryDomain categoryDomain;
    private final UserDomain userDomain;


    @Builder
    private ProductDomain(Long id, String name, Integer price, CategoryDomain categoryDomain, UserDomain userDomain) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.categoryDomain = categoryDomain;
        this.userDomain = userDomain;
    }

    // 쓰기 작업을 위한 팩토리 메서드
    public static ProductDomain createForWrite(String name, int price, CategoryDomain categoryDomain, UserDomain userDomain) {
        return new ProductDomain(null, name, price, categoryDomain, userDomain);
    }

    // 읽기 작업을 위한 팩토리 메서드
    public static ProductDomain createForRead(Long id, String name, int price, CategoryDomain categoryDomain, UserDomain userDomain) {
        return new ProductDomain(id, name, price, categoryDomain, userDomain);
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
