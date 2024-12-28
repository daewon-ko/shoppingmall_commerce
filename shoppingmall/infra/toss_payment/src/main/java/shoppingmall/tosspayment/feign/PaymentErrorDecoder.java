package shoppingmall.tosspayment.feign;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;
import shoppingmall.common.exception.ExternalApiError;
import shoppingmall.common.exception.ExternalApiException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class PaymentErrorDecoder implements ErrorDecoder {
    private final ObjectMapper objectMapper;

    public PaymentErrorDecoder(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Exception decode(String methodKey, Response response) {
        try {
            String body = new String(response.body().asInputStream().readAllBytes(), StandardCharsets.UTF_8);
            ExternalApiError paymentError = objectMapper.readValue(body, ExternalApiError.class);

            throw new ExternalApiException(paymentError.getMessage(), paymentError.getCode(), HttpStatus.BAD_REQUEST);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

