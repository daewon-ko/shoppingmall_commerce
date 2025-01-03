package shoppingmall.domainrdb.domain.order.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.lang.Nullable;
import shoppingmall.domainrdb.domain.cart.entity.Cart;
import shoppingmall.domainrdb.domain.user.entity.User;

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
    @NotNull
    String zipCode;

    @Column(name = "detail_address")
    @NotNull
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

    public void cancelOrder() {
        orderStatus = OrderStatus.CANCELED;
    }
}
