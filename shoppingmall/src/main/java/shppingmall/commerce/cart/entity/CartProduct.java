package shppingmall.commerce.cart.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import shppingmall.commerce.common.BaseEntity;
import shppingmall.commerce.product.entity.Product;

/**
 * Cart와 Product 사이의 다대다 관계를 해결하기위한
 * 중간테이블
 */
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CartProduct extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_product_id")
    @NotNull
    private Long id;

    @Column(name = "quantity")
    @NotNull
    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private Product product;


}
