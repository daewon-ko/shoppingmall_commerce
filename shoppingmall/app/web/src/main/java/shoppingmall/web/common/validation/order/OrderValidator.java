package shoppingmall.web.common.validation.order;


import shoppingmall.domainservice.common.type.request.RequestType;

public interface OrderValidator<T> {
    void validate(T requsetDTO);

    /**
     * 매개변수로 Enum을 처리
     *
     * @return
     */
    boolean isAcceptable(RequestType type);
}
