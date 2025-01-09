package shoppingmall.domainrdb.order;

import lombok.Builder;
import lombok.Getter;
import shoppingmall.domainrdb.user.UserDomain;

import java.util.List;

@Getter
public class OrderDomain {

    private OrderStatus orderStatus;
//    private List<OrderProductDomain> orderProductDomains;
    private UserDomain userDomain;
    private AddressDomain addressDomain;


    @Builder
    private OrderDomain(OrderStatus orderStatus,  UserDomain userDomain, AddressDomain addressDomain) {
        this.orderStatus = orderStatus;
//        this.orderProductDomains = orderProductDomains;
        this.userDomain = userDomain;
        this.addressDomain = addressDomain;
    }



    public void cancelOrder() {
        this.orderStatus = OrderStatus.CANCELED;
    }

}
