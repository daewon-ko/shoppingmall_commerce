package shoppingmall.domainrdb.category.entity;


import jakarta.persistence.*;
import lombok.*;
import shoppingmall.domainrdb.category.service.CategoryId;
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

    public static Category fromCategoryId(final CategoryId categoryId) {
        Category category = new Category();
        category.id = categoryId.getValue();
        return category;

    }

    public void validateCategoryName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("카테고리 이름은 null이거나 빈 문자열이 될 수 없습니다.");
        }
    }
}

