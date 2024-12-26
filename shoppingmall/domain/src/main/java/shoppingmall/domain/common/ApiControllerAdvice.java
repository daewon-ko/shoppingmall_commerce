package shoppingmall.domain.common;

import shoppingmall.common.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import shoppingmall.common.exception.ApiException;
import shoppingmall.common.exception.ExternalApiException;

@RestControllerAdvice
public class ApiControllerAdvice {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiResponse<Object>> apiException(ApiException e) {
        ApiResponse<Object> response = ApiResponse.of(e.getHttpStatusCode(), e.getMessage(), null);
//        return new ResponseEntity<>(response, e.getHttpStatusCode());
        return ResponseEntity.status(e.getHttpStatusCode()).body(response);
    }

//    @ExceptionHandler(ExternalApiException.class)
//    public ResponseEntity<ApiResponse<Object>> handleExternalApiException(ExternalApiException ex) {
//        ApiResponse<Object> response = ApiResponse.of(ex.getHttpStatus(), ex.getMessage(), null);
//        return ResponseEntity.status(ex.getHttpStatus()).body(response);
//    }


    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Object> bindException(BindException e) {
        return ApiResponse.of(HttpStatus.BAD_REQUEST,
                e.getBindingResult().getAllErrors().get(0).getDefaultMessage(), null);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Object> IllegalArgumentException(IllegalArgumentException e) {
        return ApiResponse.of(HttpStatus.BAD_REQUEST, e.getMessage(), null);
    }
}
