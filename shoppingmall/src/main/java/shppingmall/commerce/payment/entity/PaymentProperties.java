package shppingmall.commerce.payment.entity;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class PaymentProperties {
    @Value("${spring.payment.secret-key}")
    private String secretKey;



}
