package shppingmall.commerce.order.entity;

import jakarta.persistence.*;
import shppingmall.commerce.product.entity.Product;



/** TODO : OrderProduct는 생성, 수정시간이 필요할까?
 * Order에서 일괄적으로 관리해주면 되지 않을까?
 */
@Entity
@Table(name = "order_product")
public class OrderProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_product_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "quantity")
    private int quantity;
}
