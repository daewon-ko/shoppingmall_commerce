package shppingmall.commerce.product.entity;

import jakarta.persistence.*;
import lombok.*;
import shppingmall.commerce.category.entity.Category;
import shppingmall.commerce.common.BaseEntity;
import shppingmall.commerce.product.dto.response.ProductCreateResponseDto;
import shppingmall.commerce.user.entity.User;


@Entity
@Table(name = "product")
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Product extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    Long id;

    @Column(name = "product_name")
    private String name;

    @Column
    private int price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User seller;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Builder
    private Product(String name, int price, User seller, Category category) {
        this.name = name;
        this.price = price;
        this.seller = seller;
        this.category = category;
    }


    public ProductCreateResponseDto toDto() {
        return ProductCreateResponseDto.builder()
                .id(getId())
                .name(getName())
                .price(getPrice())
                .categoryId(getCategory().getId())
                .categoryName(getCategory().getName())
                .build();
    }

    public Product updateDetails(String name, int price) {
        this.name = name;
        this.price = price;
        return this;
    }
}
