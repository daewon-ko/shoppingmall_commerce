package shoppingmall.common.exception.domain;

import shoppingmall.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AuthErrorCode implements ErrorCode {
    INVALID_LOGIN_REQUEST(HttpStatus.BAD_REQUEST, "유효하지 않은 로그인 요청입니다."),

    // 토큰 관련
    BAD_TOKEN(HttpStatus.BAD_REQUEST, "인증 토큰 형식이 잘못되었습니다."),
    EXPIRED_TOKEN(HttpStatus.BAD_REQUEST, "토큰이 만료되었습니다."),


    // 세션 관련
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "잘못된 비밀번호입니다.");

    private final HttpStatus httpStatus;
    private final String message;
    }
