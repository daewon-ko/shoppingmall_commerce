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
    }
}
