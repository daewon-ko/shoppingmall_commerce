package shoppingmall.domainrdb.user;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
public class UserId {
    private final Long value;

    public static UserId from(Long value) {
        return new UserId(value);
    }
}
