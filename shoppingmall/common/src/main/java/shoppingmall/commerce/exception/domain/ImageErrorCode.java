package shoppingmall.commerce.exception.domain;

import shoppingmall.commerce.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;


@Getter
@RequiredArgsConstructor
public enum ImageErrorCode implements ErrorCode {

    NO_EXIST_IMAGE(HttpStatus.BAD_REQUEST, "해당하는 이미지가 없습니다");
    private final HttpStatus httpStatus;
    private final String message;
}
