package shoppingmall.common.exception.domain;

import shoppingmall.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum PaymentErrorCode implements ErrorCode {

    NO_EXIST_PAYMENT(HttpStatus.BAD_REQUEST, "해당하는 결제 내역이 없습니다."),
    FAIL_PAYMENT(HttpStatus.INTERNAL_SERVER_ERROR, "결제가 실패하였습니다.");



    private final HttpStatus httpStatus;
    private final String message;


}
