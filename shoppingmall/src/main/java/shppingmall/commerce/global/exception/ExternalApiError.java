package shppingmall.commerce.global.exception;

import lombok.Getter;

@Getter
public class ExternalApiError {
    private String code;
    private String message;
}
