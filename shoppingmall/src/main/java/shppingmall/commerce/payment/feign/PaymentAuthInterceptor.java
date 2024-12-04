package shppingmall.commerce.payment.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import shppingmall.commerce.payment.entity.PaymentProperties;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Slf4j
public class PaymentAuthInterceptor implements RequestInterceptor {

    private static final String AUTH_HEADER_PREFIX = "Basic ";

    private final PaymentProperties paymentProperties;


    public PaymentAuthInterceptor(PaymentProperties paymentProperties) {
        this.paymentProperties = paymentProperties;
    }

    @Override
    public void apply(RequestTemplate requestTemplate) {
        final String authHeader = createPaymentAuthorizationHeader();
        requestTemplate.header("Authorization", authHeader);
        log.info("Payment Authorization Header: {}", authHeader);
    }


    private String createPaymentAuthorizationHeader() {
        final byte[] encodedBytes = Base64.getEncoder().encode((paymentProperties.getSecretKey() + ":").getBytes(StandardCharsets.UTF_8));
        return AUTH_HEADER_PREFIX + new String(encodedBytes);
    }
}
