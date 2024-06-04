package shppingmall.commerce.order.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.lang.Nullable;
import shppingmall.commerce.cart.entity.Cart;
import shppingmall.commerce.common.BaseEntity;
import shppingmall.commerce.order.OrderStatus;

@Entity
@Table(name = "orders")
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Order extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orders_id")
    private Long id;

    @Enumerated
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






}
