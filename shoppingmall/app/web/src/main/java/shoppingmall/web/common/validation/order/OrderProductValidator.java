package shoppingmall.web.common.validation.order;

import org.springframework.stereotype.Component;
import shoppingmall.domainservice.domain.order.dto.request.OrderProductCreateRequestDto;

import java.util.List;

@Component
public class OrderProductValidator {
    public void validate(List<OrderProductCreateRequestDto> dtos) {
        for (OrderProductCreateRequestDto dto : dtos) {
            validateProductId(dto);
            validateQuantity(dto);
        }

    }

    private void validateProductId(OrderProductCreateRequestDto dto) {
        if (dto.getProductId() == null) {
            throw new IllegalArgumentException("상품 정보가 없습니다. 확인해주세요.");
        }
    }

    private void validateQuantity(OrderProductCreateRequestDto dto) {
        if (dto.getQuantity() == null) {
            throw new IllegalArgumentException("상품 수량이 없습니다. 확인해주세요.");
        }
        if (dto.getQuantity() < 0 || dto.getQuantity() > 9999) {
            throw new IllegalArgumentException("상품 수량은 0 이상 9999 이하로 입력해주세요.");
        }
    }


}
