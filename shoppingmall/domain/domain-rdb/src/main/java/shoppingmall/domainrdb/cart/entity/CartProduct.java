package shoppingmall.domainrdb.cart.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shoppingmall.domainrdb.common.BaseEntity;
import shoppingmall.domainrdb.product.entity.Product;

/**
 * Cart와 Product 사이의 다대다 관계를 해결하기위한
 * 중간테이블
 */
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CartProduct extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_product_id")
    private Long id;

    @Column(name = "quantity")
    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private Product product;


}
