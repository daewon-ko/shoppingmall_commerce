package shoppingmall.domainrdb.product.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
@EqualsAndHashCode
public class ProductId {
    private final Long value;

    public static ProductId from(Long value) {
        return new ProductId(value);
    }
}
