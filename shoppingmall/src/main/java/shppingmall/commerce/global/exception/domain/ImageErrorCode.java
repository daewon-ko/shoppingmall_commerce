package shppingmall.commerce.global.exception.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import shppingmall.commerce.global.exception.ErrorCode;


@Getter
@RequiredArgsConstructor
public enum ImageErrorCode implements ErrorCode {

    NO_EXIST_IMAGE(HttpStatus.BAD_REQUEST, "해당하는 이미지가 없습니다");
    private final HttpStatus httpStatus;
    private final String message;
}
