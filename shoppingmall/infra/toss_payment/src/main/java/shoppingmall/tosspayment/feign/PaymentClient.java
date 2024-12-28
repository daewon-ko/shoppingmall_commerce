package shoppingmall.tosspayment.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import shoppingmall.common.dto.TossPaymentConfirmRequest;
import shoppingmall.common.dto.TossPaymentConfirmResponse;

@FeignClient(name = "paymentClient", url = "${spring.payment.base-url}", configuration = PaymentConfiguration.class)
public interface PaymentClient {

    @PostMapping(value = "/confirm", consumes = MediaType.APPLICATION_JSON_VALUE)
    TossPaymentConfirmResponse confirmPayment(@RequestBody TossPaymentConfirmRequest tossPaymentConfirmRequest);

    @PostMapping(value = "/{paymentKey}/cancel")
    void cancelPayment(@PathVariable("paymentKey") String paymentKey, @RequestBody String cancelReason);


}
