package shppingmall.commerce.category.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import shppingmall.commerce.common.BaseEntity;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Category extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @Column(name = "category_name")
    @NotNull
    private String name;


    @Builder
    private Category(String name) {
        this.name = name;
    }

    public static Category createCategory(String name) {
        return Category.builder()
                .name(name).build();

    }
}

