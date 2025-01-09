package shoppingmall.common.exception.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import shoppingmall.common.exception.ErrorCode;

@Getter
@RequiredArgsConstructor
public enum CartProductErrorCode implements ErrorCode {

    NO_EXIST_CART_PRODUCT(HttpStatus.BAD_REQUEST, "해당하는 카트 상품이 존재하지 않습니다.");


    private final HttpStatus httpStatus;
    private final String message;
}
