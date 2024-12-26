package shoppingmall.web;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;

@SpringBootApplication(scanBasePackages = {
        "shoppingmall.common",
        "shoppingmall.domain",
        "shoppingmall.web",
        "shoppingmall.tosspayment"
})
//,
//        exclude = SecurityAutoConfiguration.class
//        // 없으면 404 있으면 401 왜?
//)
//@EnableFeignClients(basePackages = {"shoppingmall.tosspayment"})
//@SpringBootApplication
public class WebApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }
}
