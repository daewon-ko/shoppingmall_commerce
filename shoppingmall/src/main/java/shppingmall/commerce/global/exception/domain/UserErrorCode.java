package shppingmall.commerce.global.exception.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import shppingmall.commerce.global.exception.ErrorCode;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {

    NO_EXIST_USER(HttpStatus.BAD_REQUEST, "회원이 존재하지 않습니다."),
    INVALID_USER_INFORMATION(HttpStatus.BAD_REQUEST, "회원의 정보가 잘못 되었습니다."),
    USER_NOT_SELLER(HttpStatus.BAD_REQUEST, "해당 회원은 판매자가 아닙니다. 판매자만이 상품을 생성할수 있습니다."),
    ALREADY_REGISTERED_EMAIL(HttpStatus.BAD_REQUEST, "이미 등록된 이메일입니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
