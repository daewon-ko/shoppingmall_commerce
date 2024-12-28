package shoppingmall.domain.domain.product.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import shoppingmall.domain.domain.product.entity.Product;
import shoppingmall.domain.domain.product.entity.ProductSearchCondition;

import java.time.LocalDateTime;
import java.util.List;

import static shoppingmall.domain.domain.category.entity.QCategory.category;
import static shoppingmall.domain.domain.product.entity.QProduct.product;


@Repository
@RequiredArgsConstructor
public class ProductQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public Slice<Product> findProductsByCond(ProductSearchCondition productSearchCondition, Pageable pageable) {


        List<Product> products = jpaQueryFactory.selectFrom(product)
                .join(product.category, category)
                .fetchJoin()
                .where(categoryIdEq(productSearchCondition.getCategoryId()),
                        productNameEq(productSearchCondition.getProductName()),
                        priceLoe(productSearchCondition.getMaxPrice()),
                        priceGoe(productSearchCondition.getMinPrice()),
                        endDateLoe(productSearchCondition.getEndDate()),
                        startDateGoe(productSearchCondition.getStartDate())
                        )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        boolean hasNext = products.size() > pageable.getPageSize();

        if (hasNext) {
            products.remove(products.size() - 1);
        }

        return new SliceImpl<>(products, pageable, hasNext);

    }

    private BooleanExpression categoryIdEq(Long categoryId) {
        return StringUtils.hasText(String.valueOf(categoryId)) ? product.category.id
                .eq(categoryId) : null;
    }

    private BooleanExpression productNameEq(String productName) {
        return productName == null ? null : product.name.eq(productName);
    }

    private BooleanExpression priceGoe(Integer minPrice) {
        return minPrice == null ? null : product.price.goe(minPrice);
    }

    private BooleanExpression priceLoe(Integer maxPrice) {
        return maxPrice == null ? null : product.price.loe(maxPrice);
    }

    private BooleanExpression startDateGoe(LocalDateTime startDate) {
        return startDate == null ? null : product.createdAt.goe(startDate);
    }

    private BooleanExpression endDateLoe(LocalDateTime endDate) {
        return endDate == null ? null : product.createdAt.loe(endDate);
    }


}
