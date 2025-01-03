package shoppingmall.domainrdb.category.entity;


import jakarta.persistence.*;
import lombok.*;
import shoppingmall.domainrdb.common.BaseEntity;

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

