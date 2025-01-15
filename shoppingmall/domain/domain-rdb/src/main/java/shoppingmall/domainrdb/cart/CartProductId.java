package shoppingmall.domainrdb.cart;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public class CartProductId {
    private final Long value;

    public static CartProductId from(Long value) {
        return new CartProductId(value);
    }
}
