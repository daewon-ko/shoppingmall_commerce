package shppingmall.commerce.order.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
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
    @NotNull
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






}
