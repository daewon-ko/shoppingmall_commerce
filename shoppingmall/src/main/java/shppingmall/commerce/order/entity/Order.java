package shppingmall.commerce.order.entity;

import jakarta.persistence.*;
import shppingmall.commerce.common.BaseEntity;
import shppingmall.commerce.order.OrderStatus;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orders_id")
    private Long id;
    @Enumerated
    @Column(name = "order_status")
    OrderStatus orderStatus;

    @Column(name = "zip_code")
    String zipCode;
    @Column(name = "detail_address")
    String detailAddress;






}
