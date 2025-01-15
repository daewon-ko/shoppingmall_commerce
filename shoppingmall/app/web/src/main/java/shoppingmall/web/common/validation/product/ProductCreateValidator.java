package shoppingmall.web.common.validation.product;

import org.springframework.stereotype.Component;
import shoppingmall.domainservice.domain.product.dto.request.ProductCreateRequestDto;

@Component
public class ProductCreateValidator {
    public void validate(final ProductCreateRequestDto productCreateRequestDto) {
        if (productCreateRequestDto.getName().length() > 100) {
            throw new IllegalArgumentException("상품 이름은 100자 이하여야 합니다.");
        }

        if(productCreateRequestDto.getName() == null || productCreateRequestDto.getName().isEmpty()) {
            throw new IllegalArgumentException("상품 이름은 공백이 될 수 없습니다.");
        }

        if (productCreateRequestDto.getPrice() < 0) {
            throw new IllegalArgumentException("상품 가격은 음수가 될 수 없습니다.");
        }
        if (productCreateRequestDto.getPrice() > 9999999999L) {
            throw new IllegalArgumentException("상품 가격은 9999999999 이하여야 합니다.");
        }

        if (productCreateRequestDto.getCagegoryName().isEmpty() || productCreateRequestDto.getCagegoryName() == null) {
            throw new IllegalArgumentException("카테고리 이름을 입력해주세요.");
        }
    }
}
