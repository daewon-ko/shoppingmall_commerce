package shoppingmall.domainrdb.order.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;
import shoppingmall.domainrdb.order.domain.OrderStatus;
import shoppingmall.domainrdb.order.dto.OrderSearchCondition;
import shoppingmall.domainrdb.order.entity.OrderProduct;

import java.util.List;

import static shoppingmall.domainrdb.order.entity.QOrder.order;
import static shoppingmall.domainrdb.order.entity.QOrderProduct.orderProduct;
import static shoppingmall.domainrdb.product.entity.QProduct.product;


@Repository
@RequiredArgsConstructor
public class OrderQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;


//    public Slice<OrderProductResponseDto> findOrderProducts(Long userId, OrderSearchCondition orderSearchCondition, Pageable pageable) {
//        final OrderStatus orderStatus = orderSearchCondition.getOrderStatus();
//
//
//        List<OrderProductResponseDto> results = jpaQueryFactory
//                .select(new QOrderProductResponseDto(
//                        order.id,
//                        product.id,
//                        product.name,
//                        orderProduct.price,
//                        orderProduct.quantity
//                ))
//                .from(orderProduct)
//                .join(orderProduct.order, order)
//                .join(orderProduct.product, product)
//                .where(order.user.id.eq(userId),
//                        orderStatusEq(orderStatus)
//                )
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize() + 1)  // 페이징을 위한 +1
//                .fetch();
//
//        boolean hasNext = results.size() > pageable.getPageSize();
//
//        if (hasNext) {
//            results.remove(results.size() - 1);
//        }
//
//        return new SliceImpl<>(results, pageable, hasNext);
//
//    }

    public Slice<OrderProduct> findOrderProducts(Long userId, OrderSearchCondition orderSearchCondition, Pageable pageable) {
        final OrderStatus orderStatus = orderSearchCondition.getOrderStatus();

        List<OrderProduct> results = jpaQueryFactory
                .selectFrom(orderProduct)
                .join(orderProduct.order, order).fetchJoin()    // Order와 fetch join
                .join(orderProduct.product, product).fetchJoin() // Product와 fetch join
                .where(order.user.id.eq(userId),
                        orderStatusEq(orderStatus))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)  // 페이징을 위한 +1
                .fetch();

        boolean hasNext = results.size() > pageable.getPageSize();

        if (hasNext) {
            results.remove(results.size() - 1);
        }

        return new SliceImpl<>(results, pageable, hasNext);
    }




    private BooleanExpression orderStatusEq(OrderStatus orderStatus) {
        return orderStatus != null ? order.orderStatus.eq(orderStatus) : null;
    }


    public OrderProduct findOrderProductWithOrderIdAndProductId(Long orderId, Long productId) {

        return jpaQueryFactory.selectFrom(orderProduct)
                .leftJoin(orderProduct.order, order).fetchJoin() // Order와 fetch join
                .leftJoin(orderProduct.product, product).fetchJoin() // Product와 fetch join
                .where(order.id.eq(orderId).and(product.id.eq(productId))) // 필터링 조건은 where 절에서 처리
                .fetchOne();
    }
}
