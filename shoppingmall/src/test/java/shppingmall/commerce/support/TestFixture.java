package shppingmall.commerce.support;

import shppingmall.commerce.order.OrderStatus;
import shppingmall.commerce.order.entity.Order;
import shppingmall.commerce.order.entity.OrderProduct;
import shppingmall.commerce.product.entity.Product;
import shppingmall.commerce.user.entity.User;
import shppingmall.commerce.user.entity.UserRole;

public class TestFixture {

    public static User createUser(String name, String password, UserRole userRole) {
        User user = User.builder()
                .name(name)
                .password(password)
                .userRole(userRole)
                .build();
        return user;
    }

    public static Product createProduct(int price, String name) {
        Product product = Product.builder()
                .price(price)
                .name(name)
                .build();
        return product;
    }
    public static Order createOrder(User userA, OrderStatus orderStatus, String detailAddress, String zipCode) {
        return Order.builder()
                .orderStatus(orderStatus)
                .user(userA)
                .detailAddress(detailAddress)
                .zipCode(zipCode)
                .build();
    }

    public static OrderProduct createOrderProduct(Order savedOrder, Product savedProductB, int quantity) {
        OrderProduct orderProduct2 = OrderProduct.builder()
                .order(savedOrder)
                .product(savedProductB)
                .price(savedProductB.getPrice())
                .quantity(quantity)
                .build();
        return orderProduct2;
    }
}
