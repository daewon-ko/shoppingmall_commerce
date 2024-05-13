package shppingmall.commerce.cart.entity;

import jakarta.persistence.*;
import shppingmall.commerce.common.BaseEntity;

@Entity
public class Cart extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long id;



}
