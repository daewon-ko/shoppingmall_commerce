package shoppingmall.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public class ApiException extends RuntimeException {
    private final CommonError errorCode;



    public HttpStatus getHttpStatusCode() {
        return errorCode.getHttpStatus();
    }

    public String getMessage() {
        return errorCode.getMessage();
    }

    public String getName() {
        return errorCode.name();
    }




}
