package shppingmall.commerce.global.exception;

import org.springframework.http.HttpStatus;

public interface ErrorCode {


    HttpStatus getHttpStatus();

    String getMessage();

    String name();

}
