package shoppingmall.core.domain.order.entity;

public enum OrderStatus {
    NEW("신규생성"), READY("주문준비"), ORDER_FINISH("주문완료"), CANCELED("주문취소"), DELIVERY_FINISHED("배송완료");

    private String message;

    OrderStatus(String message) {
        this.message = message;
    }
}
