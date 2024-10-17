package shppingmall.commerce.global.exception.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import shppingmall.commerce.global.exception.ErrorCode;

@Getter
@RequiredArgsConstructor
public enum OrderErrorCode implements ErrorCode {

    ORDER_UPDATE_NOT_ALLOWED(HttpStatus.BAD_REQUEST, "주문을 수정할 수 없습니다."),
    NOT_EXIST_ORDER(HttpStatus.BAD_REQUEST, "해당하는 주문이 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
