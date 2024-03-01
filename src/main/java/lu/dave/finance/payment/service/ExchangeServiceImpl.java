package lu.dave.finance.payment.service;

import lombok.AllArgsConstructor;
import org.javamoney.moneta.Money;
import org.springframework.stereotype.Service;

import javax.money.*;
import javax.money.convert.CurrencyConversion;
import javax.money.convert.ExchangeRateProvider;
import javax.money.convert.MonetaryConversions;

@AllArgsConstructor
@Service
public class ExchangeServiceImpl implements ExchangeService {

    public CurrencyConversion getCurrencyRate(String currency) {
        try {
            ExchangeRateProvider rateProvider = MonetaryConversions.getExchangeRateProvider("ECB-HIST", "ECB-HIST90", "ECB");
            CurrencyConversion conversion = rateProvider.getCurrencyConversion(currency);
            return conversion;
        } catch (MonetaryException e) {
            throw e;
            // throw ServiceUnvailable exception
        }

    }

    public boolean isCurrencyAvailable(String currencyCode) {
        try {
            Monetary.getCurrency(currencyCode);
            return true;
        } catch (MonetaryException e) {
            // Currency not found or not supported exception
            return false;
        }
    }

    public double convertAmount(String fromCurrency, double fromAmount, String toCurrency) {
        Money fromAmountWithCurr = Money.of(fromAmount, fromCurrency);
        CurrencyConversion conversion = getCurrencyRate(toCurrency);

        // Perform conversion using exchange rates
        MonetaryAmount convertedAmount = fromAmountWithCurr.with(conversion);
        return convertedAmount.getNumber().doubleValueExact();
    }

}
