package shoppingmall.domainrdb;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@Configuration
@ComponentScan(basePackages = {
        "shoppingmall.common",
        "shoppingmall.domainrdb",
        "shoppingmall.tosspayment"
})
@EntityScan(basePackages = "shoppingmall.domainrdb")    // JPA Entity를 찾기 위한 설정
//@EnableFeignClients(basePackages = {"shoppingmall.tosspayment"})
public class DomainRdbConfiguration {
}
