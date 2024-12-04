package shppingmall.commerce.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ExternalApiException extends RuntimeException {
    private final String externalMessage;
    private final String externalErrorCode;
    private final HttpStatus httpStatus;

    public ExternalApiException(String externalMessage, String externalErrorCode, HttpStatus httpStatus) {
        super(externalMessage);
        this.httpStatus = httpStatus;
        this.externalMessage = externalMessage;
        this.externalErrorCode = externalErrorCode;
    }
}
