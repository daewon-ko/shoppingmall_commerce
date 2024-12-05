package shppingmall.commerce.order.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.lang.Nullable;
import shppingmall.commerce.cart.entity.Cart;
import shppingmall.commerce.common.BaseEntity;
import shppingmall.commerce.order.OrderStatus;
import shppingmall.commerce.user.entity.User;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "orders")
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter

public class Order extends BaseEntity implements Serializable {
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

    public void cancelOrder() {
        orderStatus = OrderStatus.CANCELED;
    }
}
