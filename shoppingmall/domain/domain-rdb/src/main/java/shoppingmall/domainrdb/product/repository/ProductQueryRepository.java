package shoppingmall.domainrdb.product.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import shoppingmall.domainrdb.product.dto.request.ProductSearchCondition;
import shoppingmall.domainrdb.product.entity.Product;

import java.time.LocalDateTime;
import java.util.List;

import static shoppingmall.domainrdb.category.entity.QCategory.*;
import static shoppingmall.domainrdb.product.entity.QProduct.*;
import static shoppingmall.domainrdb.user.entity.QUser.*;


@Repository
@RequiredArgsConstructor
public class ProductQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public Slice<Product> findProductsByCond(final ProductSearchCondition productSearchCondition, Pageable pageable) {


        List<Product> products = jpaQueryFactory.selectFrom(product)
                .join(product.category, category)
                .join(product.seller, user)
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
        return categoryId != null ? product.category.id.eq(categoryId) : null;

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
