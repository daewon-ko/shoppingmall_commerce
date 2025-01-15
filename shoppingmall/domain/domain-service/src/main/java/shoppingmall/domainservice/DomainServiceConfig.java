package shoppingmall.domainservice;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(
        scanBasePackages = {
                "shoppingmall.domainservice.domain",
                "shoppingmall.aws.s3",
                "shoppingmall.domainrdb",
                "shoppingmall.domainredis"
        }
)
@EnableFeignClients(basePackages = {"shoppingmall.tosspayment"})
public class DomainServiceConfig {
}
