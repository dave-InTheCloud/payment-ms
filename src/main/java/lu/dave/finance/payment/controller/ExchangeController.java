package lu.dave.finance.payment.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lu.dave.finance.payment.service.ExchangeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.money.MonetaryAmount;
import javax.money.convert.CurrencyConversion;
import javax.money.convert.ExchangeRate;
import javax.money.convert.ExchangeRateProvider;

import static lu.dave.finance.payment.config.ServletConfig.basePathApi;


@AllArgsConstructor
@RestController
@RequestMapping(basePathApi+"/currencies")
public class ExchangeController {

    private final ExchangeService exchangeServiceImpl;

    @Operation(summary = "Default operation", description = "This operation describes your endpoint")
    @GetMapping("")
    public ExchangeRate getRate(@RequestParam(name = "fromCurrency") String fromCurrency,
                                @RequestParam(name = "toCurrency") String  toCurrency){
        return exchangeServiceImpl.getCurrencyRate(fromCurrency, toCurrency);

    }
    @Operation(summary = "Default operation", description = "This operation describes your endpoint")
    @GetMapping("/convert")
    public Double convert(@RequestParam(name = "fromCurrency") String fromCurrency,
                                  @RequestParam(name = "toCurrency") String  toCurrency,
                                  @RequestParam(name = "amount") Double amount){
        return exchangeServiceImpl.convertAmount(fromCurrency, toCurrency, amount).getNumber().doubleValue();
    }

}
