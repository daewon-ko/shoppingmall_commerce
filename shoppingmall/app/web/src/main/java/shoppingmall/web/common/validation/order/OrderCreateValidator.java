package shoppingmall.web.common.validation.order;

import org.springframework.stereotype.Component;
import shoppingmall.domainservice.common.type.request.RequestType;
import shoppingmall.domainservice.domain.order.dto.request.OrderCreateRequestDto;

@Component
public class OrderCreateValidator implements OrderValidator<OrderCreateRequestDto>{


    /**
     * 기존에 순서가 있다.
     * validate하는 경우 순서가 있을 수 있다.
     * <p>
     * OrderValidator의 순서를 어떻게 정의할 수 있을까?
     * <p>
     * -> @Order()가능하다.
     * 그러나 번거로울 수있다.
     * <p>
     * 중간에 것은 그러면 어떻게?
     * enum같은 것으로 정의해서 처리하는 방법(?)
     * enum에서 순서로 아예 정의한다.
     * enum순서로 Validate를 할 수있게끔..
     * 그러나 단점은 , Validation추가할때마다 enum도 수정해야한다.
     * <p>
     * Order를 만약 짜지않았다면 어떤 Bean부터 등록될까?
     * -> 패키지 내에 정의되어있는 알파벳(?) 순서로 탄다.
     * <p>
     * Validator는 순서가 크게 중요하지 않을 수 있으나 DI개념을 이용해서 인터페이스를 통해
     * 패턴화 하는게 더 깔끔하다.
     */


    @Override
    public void validate(final OrderCreateRequestDto orderCreateRequestDto) {

        if (orderCreateRequestDto.getOrderProductRequestDtoList().isEmpty()) {
            throw new IllegalArgumentException("주문 상품 목록이 비어있을 수 없습니다.");
        }

        if (orderCreateRequestDto.getZipCode().isEmpty() || orderCreateRequestDto.getDetailAddress().isEmpty()) {
            throw new IllegalArgumentException("주소가 입력되지 않았습니다.");
        }

        if (orderCreateRequestDto.getUserId() == null) {
            throw new IllegalArgumentException("사용자 정보가 없습니다. 확인해주세요.");
        }

    }


    @Override
    public boolean isAcceptable(RequestType type) {
        return type.equals(RequestType.ORDER_CREATE);
    }
}
