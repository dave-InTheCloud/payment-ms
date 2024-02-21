package lu.dave.finance.payment.paymentMs.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PayementController {

    @GetMapping("/")
    String home() {
        return "Hello World!";
    }
}
