package lu.dave.finance.payment.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lu.dave.finance.payment.service.ExchangeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static lu.dave.finance.payment.config.ServletConfig.BAS_PATH_API;


@AllArgsConstructor
@RestController
@RequestMapping(BAS_PATH_API +"/currencies")
public class ExchangeController {

    private final ExchangeService exchangeServiceImpl;

    @Operation(summary = "Get the rate from one currency to another")
    @GetMapping("/rates")
    public double getRate(@RequestParam(name = "fromCurrency") String fromCurrency,
                               @RequestParam(name = "toCurrency") String  toCurrency){
        return exchangeServiceImpl.getCurrencyRate(fromCurrency, toCurrency).getFactor().doubleValue();

    }
    @Operation(summary = "Convert amount from one currency to another")
    @GetMapping("/convert")
    public Double convert(@RequestParam(name = "fromCurrency") String fromCurrency,
                                  @RequestParam(name = "toCurrency") String  toCurrency,
                                  @RequestParam(name = "amount") Double amount){
        return exchangeServiceImpl.convertAmount(fromCurrency, toCurrency, amount);
    }

}
