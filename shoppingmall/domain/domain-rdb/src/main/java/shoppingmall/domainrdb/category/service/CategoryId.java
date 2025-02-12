package shoppingmall.domainrdb.category.service;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class CategoryId {
    private final Long value;

    public static CategoryId from(Long value) {
        return new CategoryId(value);
    }
}
