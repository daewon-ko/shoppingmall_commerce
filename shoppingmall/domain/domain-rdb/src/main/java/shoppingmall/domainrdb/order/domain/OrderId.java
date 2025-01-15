package shoppingmall.domainrdb.order.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@RequiredArgsConstructor
@ToString
public class OrderId {
    private final Long value;

    public static OrderId from(Long value) {
        return new OrderId(value);
    }
}
