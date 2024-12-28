package shoppingmall.common.exception;

import org.springframework.http.HttpStatus;

public interface CommonError {
    HttpStatus getHttpStatus();
    String getMessage();
    String name();

    default String getCode() {
        return name();
    }
}
