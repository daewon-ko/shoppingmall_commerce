package shppingmall.commerce.product.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.apache.catalina.webresources.AbstractResource;
import shppingmall.commerce.category.entity.Category;
import shppingmall.commerce.common.BaseEntity;
import shppingmall.commerce.image.entity.Image;
import shppingmall.commerce.product.dto.response.ProductResponseDto;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product")
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
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


    @Column(name = "image_url")
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;


    public ProductResponseDto toDto() {
        return ProductResponseDto.builder()
                .id(getId())
                .name(getName())
                .price(getPrice())
                .imageUrl(getImageUrl())
                .categoryId(getCategory().getId())
                .categoryName(getCategory().getName())
                .build();
    }
}
