package shoppingmall.common.exception.domain;

import shoppingmall.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ProductErrorCode implements ErrorCode {
    PRODUCT_NOT_FOUND(HttpStatus.BAD_REQUEST, "해당하는 상품이 없습니다."),
    PRODUCT_PRICE_CHANGED(HttpStatus.BAD_REQUEST, "상품 가격이 변경되었습니다. 새로고침해주세요.");

    private final HttpStatus httpStatus;
    private final String message;

}
