package shppingmall.commerce.global.exception.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import shppingmall.commerce.global.exception.ErrorCode;

@Getter
@RequiredArgsConstructor
public enum CartErrorCode implements ErrorCode {

    NO_EXIST_CART(HttpStatus.BAD_REQUEST, "해당하는 카트가 존재하지 않습니다."),
    NO_EXIST_CART_NUMBER(HttpStatus.BAD_REQUEST, "카트번호가 없습니다. 재확인해주세요.");

    private final HttpStatus httpStatus;
    private final String message;

}
