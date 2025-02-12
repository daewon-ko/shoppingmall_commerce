package shoppingmall.domainrdb.payment;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
public class TossPaymentId {
    private final Long value;

    public static TossPaymentId from(Long value) {
        return new TossPaymentId(value);
    }
}
