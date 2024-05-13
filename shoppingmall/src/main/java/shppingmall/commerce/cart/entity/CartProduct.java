package shppingmall.commerce.cart.entity;

import jakarta.persistence.*;
import shppingmall.commerce.common.BaseEntity;
import shppingmall.commerce.product.entity.Product;

/**
 * Cart와 Product 사이의 다대다 관계를 해결하기위한
 * 중간테이블
 */
@Entity
public class CartProduct extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_product_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;


}
