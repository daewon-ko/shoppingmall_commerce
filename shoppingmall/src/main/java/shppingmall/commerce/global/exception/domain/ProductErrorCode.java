package shppingmall.commerce.global.exception.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import shppingmall.commerce.global.exception.ErrorCode;

@RequiredArgsConstructor
@Getter
public enum ProductErrorCode implements ErrorCode {
    PRODUCT_NOT_FOUND(HttpStatus.BAD_REQUEST, "해당하는 상품이 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;

}
