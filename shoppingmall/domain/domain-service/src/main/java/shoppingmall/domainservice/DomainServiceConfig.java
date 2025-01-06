package shoppingmall.domainservice;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
        scanBasePackages = {
                "shoppingmall.domainservice.domain",
                "shoppingmall.aws.s3",
                "shoppingmall.domainrdb",
                "shoppingmall.domainredis"
        }
)
public class DomainServiceConfig {
}
