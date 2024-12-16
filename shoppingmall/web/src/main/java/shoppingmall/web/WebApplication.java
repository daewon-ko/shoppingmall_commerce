package shoppingmall.web;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {
        "shoppingmall.common",
        "shoppingmall.core",
        "shoppingmall.web"
//        "shoppingmall.infra",

})
@EnableFeignClients(basePackages = {"shoppingmall.tosspayment"})
public class WebApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }
}
