package shoppingmall.web.api.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @GetMapping("/checkout")
    public String showCheckout() {
        return "checkout";
    }

    @GetMapping("/success.html")
    public String success(
            @RequestParam("paymentType") String paymentType,
            @RequestParam("orderId") String orderId,
            @RequestParam("paymentKey") String paymentKey,
            @RequestParam("amount") int amount ) {
        return "success";
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }

}
