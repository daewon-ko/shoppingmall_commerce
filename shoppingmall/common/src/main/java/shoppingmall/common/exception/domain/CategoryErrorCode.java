package shoppingmall.common.exception.domain;

import shoppingmall.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CategoryErrorCode implements ErrorCode {
    NO_EXIST_CATEGORY(HttpStatus.NOT_FOUND, "카테고리가 존재하지 않습니다.");

    private final HttpStatus httpStatus;
    private final String message;

}
