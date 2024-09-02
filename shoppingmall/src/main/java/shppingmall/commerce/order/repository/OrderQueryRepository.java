package shppingmall.commerce.order.repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;
import shppingmall.commerce.order.OrderStatus;
//import shppingmall.commerce.order.dto.QOrderListResponseDto;
import shppingmall.commerce.order.dto.request.OrderSearchCondition;
import shppingmall.commerce.order.dto.response.OrderProductResponseDto;
import shppingmall.commerce.order.dto.response.QOrderProductResponseDto;

import java.util.List;

import static shppingmall.commerce.order.entity.QOrder.order;
import static shppingmall.commerce.order.entity.QOrderProduct.orderProduct;
import static shppingmall.commerce.product.entity.QProduct.product;


@Repository
@RequiredArgsConstructor
public class OrderQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;


    public Slice<OrderProductResponseDto> findOrderProducts(Long userId,  OrderSearchCondition orderSearchCondition, Pageable pageable) {
        final OrderStatus orderStatus = orderSearchCondition.getOrderStatus();


        List<OrderProductResponseDto> results = jpaQueryFactory
                .select(new QOrderProductResponseDto(
                        order.id,
                        product.id,
                        product.name,
                        orderProduct.price,
                        orderProduct.quantity
                ))
                .from(orderProduct)
                .join(orderProduct.order, order)
                .join(orderProduct.product, product)
                .where(order.user.id.eq(userId),
                        orderStatusEq(orderStatus)
                )
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


}
