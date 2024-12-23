package shoppingmall.common.exception.domain;

import shoppingmall.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum S3UploaderErrorCode implements ErrorCode {

    INVALID_FILE_NAME(HttpStatus.BAD_REQUEST,"파일이름이 유효하지 않습니다."),
    EMPTY_FILE(HttpStatus.BAD_REQUEST, "비어있는 파일입니다."),
    UNEXPECTED_GET_URL_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "예상치 못한 오류로 이미지 URL 조회를 실패했습니다.");


    private final HttpStatus httpStatus;
    private final String message;

}
