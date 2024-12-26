package shoppingmall.web;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

//@SpringBootApplication(scanBasePackages = {
//        "shoppingmall.common",
//        "shoppingmall.domain-service",
//        "shoppingmall.web"
//}
//,
//        exclude = SecurityAutoConfiguration.class
//        // 없으면 404 있으면 401 왜?
//)
//@EnableFeignClients(basePackages = {"shoppingmall.tosspayment"})
@SpringBootApplication
public class WebApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }
}
