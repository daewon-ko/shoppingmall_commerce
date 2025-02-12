package shoppingmall.domainrdb.order.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;
import shoppingmall.domainrdb.cart.entity.Cart;
import shoppingmall.domainrdb.order.domain.OrderId;
import shoppingmall.domainrdb.order.domain.OrderStatus;
import shoppingmall.domainrdb.user.entity.User;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter

public class Order implements Serializable {
    @Serial
    private static final long serialVersionUID = -4316946379860671944L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "orders_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    OrderStatus orderStatus;

    @Column(name = "zip_code")
    String zipCode;

    @Column(name = "detail_address")
    String detailAddress;

    // TODO : cascade option을 줘야할까?( 해당 옵션을 주면, cart가 삭제될때 주문도 삭제된다.)
    @ManyToOne(fetch = FetchType.LAZY)
    @Nullable
    Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    User user;

    @Builder
    private Order(Long id, OrderStatus orderStatus, String zipCode, String detailAddress, @Nullable Cart cart, User user) {
        this.id = id;
        this.orderStatus = orderStatus;
        this.zipCode = zipCode;
        this.detailAddress = detailAddress;
        this.cart = cart;
        this.user = user;
    }

    public static Order fromOrderId(final OrderId orderId) {
        Order order = new Order();
        order.id = orderId.getValue();
        return order;
    }

    public void cancelOrder() {
        orderStatus = OrderStatus.CANCELED;
    }

//    public static OrderDomain toDomain() {
//
//    }
}
