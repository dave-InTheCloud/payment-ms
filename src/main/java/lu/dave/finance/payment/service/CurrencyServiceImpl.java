package lu.dave.finance.payment.service;

import lombok.AllArgsConstructor;
import org.javamoney.moneta.Money;
import org.springframework.stereotype.Service;

import javax.money.MonetaryAmount;
import javax.money.convert.CurrencyConversion;
import javax.money.convert.ExchangeRateProvider;
import javax.money.convert.MonetaryConversions;

@AllArgsConstructor
@Service
public class CurrencyServiceImpl implements CurrencyService {


    public double convertAmount(String fromCurrency, double fromAmount, String toCurrency) {
        Money fromAmountWithCurr = Money.of(fromAmount, fromCurrency);
        //ExchangeRateProvider rateProvider = MonetaryConversions.getExchangeRateProvider();
        ExchangeRateProvider rateProvider = MonetaryConversions.getExchangeRateProvider( "ECB-HIST", "ECB-HIST90", "ECB" );
        CurrencyConversion conversion = rateProvider.getCurrencyConversion(toCurrency);

        // Perform conversion using exchange rates
        MonetaryAmount convertedAmount = fromAmountWithCurr.with(conversion);
        return convertedAmount.getNumber().doubleValueExact();
    }

}
