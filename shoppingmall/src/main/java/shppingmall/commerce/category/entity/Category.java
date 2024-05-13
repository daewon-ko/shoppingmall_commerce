package shppingmall.commerce.category.entity;


import jakarta.persistence.*;
import shppingmall.commerce.common.BaseEntity;

@Entity
public class Category extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @Column(name = "category_name")
    private String name;

}
