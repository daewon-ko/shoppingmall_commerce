package shoppingmall.tosspayment.feign;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;

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
