package shppingmall.commerce.payment.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import shppingmall.commerce.payment.dto.TossPaymentConfirmRequest;
import shppingmall.commerce.payment.dto.TossPaymentConfirmResponse;

@FeignClient(name = "paymentClient", url = "${spring.payment.base-url}", configuration = PaymentConfiguration.class)
public interface PaymentClient {

    @PostMapping(value = "/confirm", consumes = MediaType.APPLICATION_JSON_VALUE)
    TossPaymentConfirmResponse confirmPayment(@RequestBody TossPaymentConfirmRequest tossPaymentConfirmRequest);
}
