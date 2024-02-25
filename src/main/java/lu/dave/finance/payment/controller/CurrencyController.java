package lu.dave.finance.payment.controller;

import lombok.AllArgsConstructor;
import lu.dave.finance.payment.service.CurrencyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@AllArgsConstructor
@RestController
@RequestMapping("/currencies")
public class CurrencyController {
    private final CurrencyService currencyServiceImpl;

    @GetMapping({"/", ""})
    public double convert(){
        return currencyServiceImpl.convertAmount("USD", 1300.00, "EUR");
    }

}
