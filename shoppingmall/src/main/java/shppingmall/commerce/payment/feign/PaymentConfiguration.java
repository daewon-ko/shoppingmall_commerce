package shppingmall.commerce.payment.feign;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import shppingmall.commerce.payment.entity.PaymentProperties;

public class PaymentConfiguration {

    private final PaymentProperties paymentProperties;


    public PaymentConfiguration(PaymentProperties paymentProperties) {
        this.paymentProperties = paymentProperties;
    }


    @Bean
    public PaymentErrorDecoder paymentErrorDecoder(ObjectMapper objectMapper) {
        return new PaymentErrorDecoder(objectMapper);
    }

    @Bean
    public PaymentAuthInterceptor paymentAuthInterceptor() {
        return new PaymentAuthInterceptor(paymentProperties);
    }

}
