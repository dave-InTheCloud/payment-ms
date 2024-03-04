package lu.dave.finance.payment.service;

import lombok.AllArgsConstructor;
import lu.dave.finance.payment.exception.BadParameterException;
import lu.dave.finance.payment.exception.ServiceUnvailableException;
import org.javamoney.moneta.Money;
import org.springframework.stereotype.Service;

import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.money.MonetaryException;
import javax.money.convert.CurrencyConversion;
import javax.money.convert.ExchangeRateProvider;
import javax.money.convert.MonetaryConversions;

@AllArgsConstructor
@Service
public class ExchangeServiceImpl implements ExchangeService {

    public CurrencyConversion getCurrencyRate(final String currency) {
        try {
            ExchangeRateProvider rateProvider = MonetaryConversions.getExchangeRateProvider("ECB-HIST", "ECB-HIST90", "ECB");
            CurrencyConversion conversion = rateProvider.getCurrencyConversion(currency);
            return conversion;
        } catch (MonetaryException e) {
            throw new ServiceUnvailableException("The currency of the service is unavailable");
        }

    }

    public boolean isCurrencyAvailable(final String currencyCode) {
        try {
            Monetary.getCurrency(currencyCode);
            return true;
        } catch (MonetaryException e) {
            throw new BadParameterException(String.format("Currency or service not available %s", currencyCode));
        }
    }

    public MonetaryAmount convertAmount(final String fromCurrency, final String toCurrency, final Double fromAmount) {
        try {
            Money fromAmountWithCurr = Money.of(fromAmount, fromCurrency);
            CurrencyConversion conversion = getCurrencyRate(toCurrency);
            // Perform conversion using exchange rates
            MonetaryAmount convertedAmount = fromAmountWithCurr.with(conversion);
            return convertedAmount;
        } catch (MonetaryException e) {

            throw new BadParameterException(String.format("Conversion failed or service not available %s to %S", fromCurrency, toCurrency));
        }
    }

}
