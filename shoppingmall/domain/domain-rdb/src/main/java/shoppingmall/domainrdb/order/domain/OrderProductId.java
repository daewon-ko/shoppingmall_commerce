package shoppingmall.domainrdb.order.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public class OrderProductId {
    private final Long value;

    public static OrderProductId from(Long value) {
        return new OrderProductId(value);
    }
}
