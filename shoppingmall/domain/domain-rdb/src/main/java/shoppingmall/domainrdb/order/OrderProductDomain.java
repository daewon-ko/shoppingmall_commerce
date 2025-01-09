package shoppingmall.domainrdb.order;

import lombok.Builder;
import lombok.Getter;
import shoppingmall.domainrdb.product.ProductDomain;

@Getter
public class OrderProductDomain {
    private int quantity;
    private ProductDomain productDomain;
    private OrderDomain orderDomain;
    //TODO : OrderDomain을 추가해야할까?


    @Builder
    private OrderProductDomain(int quantity, ProductDomain productDomain, OrderDomain orderDomain) {
        this.quantity = quantity;
        this.productDomain = productDomain;
        this.orderDomain = orderDomain;
    }




    public void changeQuantity(int quantity) {
        this.quantity = quantity;
    }

}
