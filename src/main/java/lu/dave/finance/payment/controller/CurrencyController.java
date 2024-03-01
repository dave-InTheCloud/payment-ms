package lu.dave.finance.payment.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lu.dave.finance.payment.service.ExchangeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@AllArgsConstructor
@RestController
@RequestMapping("/currencies")
public class CurrencyController {
    private final ExchangeService exchangeServiceImpl;

    @Operation(summary = "Default operation", description = "This operation describes your endpoint")
    @GetMapping({"/", ""})
    public double convert(){
        return exchangeServiceImpl.convertAmount("USD", 1300.00, "EUR");
    }

}
