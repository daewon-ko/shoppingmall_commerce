package shoppingmall.domainrdb.product;

import lombok.Getter;
import shoppingmall.domainrdb.category.CategoryDomain;
import shoppingmall.domainrdb.user.UserDomain;

@Getter
public class ProductDomain {
    private String name;
    private int price;
    private CategoryDomain categoryDomain;
    private UserDomain userDomain;


    public ProductDomain(String name, int price, CategoryDomain categoryDomain, UserDomain userDomain) {
        this.name = name;
        this.price = price;
        this.categoryDomain = categoryDomain;
        this.userDomain = userDomain;
        // 생성시 내부 Validate 진행
        validate();
    }

    private void validate() {
        categoryDomain.validate();
        userDomain.validate();
        validateName();
        validatePrice();
    }


    private void validateName() {
        if(name == null || name.isEmpty()) {
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
