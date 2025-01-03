package shoppingmall.domainrdb.order.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shoppingmall.domainrdb.domain.product.entity.Product;



/** TODO : OrderProduct는 생성, 수정시간이 필요할까?
 * Order에서 일괄적으로 관리해주면 되지 않을까?
 */
@Entity
@Table(name = "order_product")
@NoArgsConstructor
@Getter
public class OrderProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_product_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "quantity")
    private int quantity;

    @Column
    private int price;


    @Builder
    private OrderProduct(Long id, Order order, Product product, int quantity, int price) {
        this.id = id;
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
    }

    public void changeQuantity(int quantity) {
        this.quantity = quantity;
    }
}
