package shoppingmall.common.exception;

import lombok.Getter;

@Getter
public class ExternalApiError {
    private String code;
    private String message;
}
