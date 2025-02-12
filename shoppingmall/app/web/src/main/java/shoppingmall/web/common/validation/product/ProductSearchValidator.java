package shoppingmall.web.common.validation.product;

import org.springframework.stereotype.Component;
import shoppingmall.domainservice.domain.product.dto.request.ProductSearchConditionRequestDto;

@Component
public class ProductSearchValidator {
    public void validate(ProductSearchConditionRequestDto productSearchConditionRequestDto) {
        if (productSearchConditionRequestDto.getMinPrice() > productSearchConditionRequestDto.getMaxPrice()) {
            throw new IllegalArgumentException("최소 금액은 최대 금액보다 작아야 합니다.");
        }

        if (productSearchConditionRequestDto.getMinPrice() < 0) {
            throw new IllegalArgumentException("최소 금액은 0원 이상이어야 합니다.");
        }
        if (productSearchConditionRequestDto.getMaxPrice() > 999999999) {
            throw new IllegalArgumentException("최대 금액은 10억 이하입니다.");
        }

    }
}
