package shppingmall.commerce.product.entity;

import jakarta.persistence.*;
import lombok.*;
import org.apache.catalina.webresources.AbstractResource;
import shppingmall.commerce.category.entity.Category;
import shppingmall.commerce.common.BaseEntity;
import shppingmall.commerce.image.entity.Image;
import shppingmall.commerce.product.dto.response.ProductResponseDto;

import java.util.ArrayList;
import java.util.List;

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
    @JoinColumn(name = "category_id")
    private Category category;

    @Builder
    private Product(String name, int price, Category category) {
        this.name = name;
        this.price = price;
        this.category = category;
    }

    @Builder
    private Product(String name, int price) {
        this(name, price, null);
    }

    public ProductResponseDto toDto() {
        return ProductResponseDto.builder()
                .id(getId())
                .name(getName())
                .price(getPrice())
                .categoryId(getCategory().getId())
                .categoryName(getCategory().getName())
                .build();
    }
}
