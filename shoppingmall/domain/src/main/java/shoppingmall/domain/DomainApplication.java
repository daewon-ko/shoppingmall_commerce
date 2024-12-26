package shoppingmall.domain;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//@EnableFeignClients(basePackages = {"shoppingmall.tosspayment"})
@Configuration
@EnableJpaRepositories
@ComponentScan(basePackages = {
        "shoppingmall.common",
        "shoppingmall.infra",
        "shoppingmall.domain",
        "shoppingmall.tosspayment"
})
@EntityScan(basePackages = "shoppingmall.domain")
@EnableFeignClients(basePackages = {"shoppingmall.tosspayment"})
public class DomainApplication {

}
