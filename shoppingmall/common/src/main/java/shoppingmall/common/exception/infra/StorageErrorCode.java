package shoppingmall.common.exception.infra;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import shoppingmall.common.exception.ErrorCode;

@Getter
@RequiredArgsConstructor

public enum StorageErrorCode implements ErrorCode {
    // 400 Bad Request
//    HttpStatus.BAD_REQUEST
//
//
//    MISSING_REQUIRED_PARAMETER(,"필수 파라미터가 누락되었습니다."),
//    INVALID_EXTENSION("지원하지 않는 확장자입니다."),
//    INVALID_TYPE("지원하지 않는 타입입니다."),
//    INVALID_FILE("올바르지 않은 파일입니다."),

    // 404 Not Found
    NOT_FOUND(HttpStatus.NOT_FOUND, "요청한 리소스를 찾을 수 없습니다."),

    // 500
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류입니다.");


    private final HttpStatus httpStatus;
    private final String message;
}
