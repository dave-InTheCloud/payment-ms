package lu.dave.finance.payment.service;

import lombok.AllArgsConstructor;
import lu.dave.finance.payment.exception.BadParameterException;
import lu.dave.finance.payment.exception.ServiceUnvailableException;
import org.javamoney.moneta.Money;
import org.springframework.stereotype.Service;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.money.MonetaryException;
import javax.money.convert.*;

@AllArgsConstructor
@Service
public class ExchangeServiceImpl implements ExchangeService {

    public CurrencyConversion getCurrencyRateConversion(final String currency) {
        try {
            ExchangeRateProvider rateProvider = MonetaryConversions.getExchangeRateProvider( "ECB");
            CurrencyConversion conversion = rateProvider.getCurrencyConversion(currency);
            return conversion;
        } catch (MonetaryException e) {
            throw new ServiceUnvailableException("The currency of the service is unavailable");
        }

    }

    public ExchangeRate getCurrencyRate(final String fromCurrency, final String toCurrency) {
        try {
            ExchangeRateProvider rateProvider = MonetaryConversions.getExchangeRateProvider( "ECB");
            return  rateProvider.getExchangeRate(fromCurrency, toCurrency);
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
            CurrencyConversion conversion = getCurrencyRateConversion(toCurrency);
            // Perform conversion using exchange rates
            MonetaryAmount convertedAmount = fromAmountWithCurr.with(conversion);
            return convertedAmount;
        } catch (MonetaryException e) {

            throw new BadParameterException(String.format("Conversion failed or service not available %s to %S", fromCurrency, toCurrency));
        }
    }

}
