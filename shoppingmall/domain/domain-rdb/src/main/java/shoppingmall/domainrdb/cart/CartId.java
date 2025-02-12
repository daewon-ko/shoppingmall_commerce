package shoppingmall.domainrdb.cart;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
public class CartId {
    private final Long value;

    public static CartId from(Long value) {
        return new CartId(value);
    }

}
