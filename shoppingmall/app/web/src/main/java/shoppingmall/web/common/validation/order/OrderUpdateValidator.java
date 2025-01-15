package shoppingmall.web.common.validation.order;


import org.springframework.stereotype.Component;
import shoppingmall.domainservice.domain.order.dto.request.OrderProductUpdateRequest;

import java.util.List;

@Component
public class OrderUpdateValidator {
    public void validate(Long orderId, List<OrderProductUpdateRequest> updateRequestList) {
        if (orderId == null) {
            throw new IllegalArgumentException("주문 정보가 없습니다. 확인해주세요.");
        }
        if (updateRequestList == null || updateRequestList.isEmpty()) {
            throw new IllegalArgumentException("주문 상품 정보가 없습니다. 확인해주세요.");
        }
        for (OrderProductUpdateRequest dto : updateRequestList) {
            validateProductId(dto);
            validateQuantity(dto);
        }

    }

    private void validateProductId(OrderProductUpdateRequest dto) {
        if (dto.getProductId() == null) {
            throw new IllegalArgumentException("상품 정보가 없습니다. 확인해주세요.");
        }
    }

    private void validateQuantity(OrderProductUpdateRequest dto) {
        if (dto.getQuantity() == null) {
            throw new IllegalArgumentException("상품 수량이 없습니다. 확인해주세요.");
        }
        if (dto.getQuantity() < 0 || dto.getQuantity() > 9999) {
            throw new IllegalArgumentException("상품 수량은 0 이상 9999 이하로 입력해주세요.");
        }
    }

}
