package shoppingmall.web.common.validation.order;

import org.springframework.stereotype.Component;
import shoppingmall.domainservice.domain.order.dto.request.OrderCreateRequestDto;

@Component
public class OrderCreateValidator {
    public void validate(OrderCreateRequestDto dto) {
        if (dto.getZipCode().isEmpty() || dto.getDetailAddress().isEmpty()) {
            throw new IllegalArgumentException("주소가 입력되지 않았습니다.");
        }
        if (dto.getOrderProductRequestDtoList().isEmpty()) {
            throw new IllegalArgumentException("주문 상품 목록이 비어있을 수 없습니다.");
        }

        if (dto.getUserId() == null) {
            throw new IllegalArgumentException("사용자 정보가 없습니다. 확인해주세요.");
        }
    }

}
