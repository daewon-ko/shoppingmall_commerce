package shppingmall.commerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import shppingmall.commerce.payment.feign.PaymentClient;


@SpringBootApplication
@EnableJpaAuditing
@EnableFeignClients(clients = PaymentClient.class)
public class CommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommerceApplication.class, args);
	}


}
