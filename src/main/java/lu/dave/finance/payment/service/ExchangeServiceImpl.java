package lu.dave.finance.payment.service;

import lombok.AllArgsConstructor;
import lu.dave.finance.payment.exception.BadParameterException;
import lu.dave.finance.payment.exception.ServiceUnvailableException;
import org.javamoney.moneta.Money;
import org.springframework.stereotype.Service;

import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.money.MonetaryException;
import javax.money.convert.*;
import java.net.UnknownHostException;

@AllArgsConstructor
@Service
public class ExchangeServiceImpl implements ExchangeService {

    public static final String SERVICE_UNAVAILABLE = "The currency or the service is unavailable";
    private static final String[] PROVIDERS = {"ECB"};

    public CurrencyConversion getCurrencyRateConversion(final String currency) {
        try {
            MonetaryConversions.isConversionAvailable(currency, PROVIDERS);
            ExchangeRateProvider rateProvider = MonetaryConversions.getExchangeRateProvider(PROVIDERS);
            CurrencyConversion conversion = rateProvider.getCurrencyConversion(currency);
            return conversion;
        } catch (Exception e) {
            throw new ServiceUnvailableException(SERVICE_UNAVAILABLE);
        }

    }

    public ExchangeRate getCurrencyRate(final String fromCurrency, final String toCurrency) {
        try {
            ExchangeRateProvider rateProvider = MonetaryConversions.getExchangeRateProvider(PROVIDERS);
            return rateProvider.getExchangeRate(fromCurrency, toCurrency);
        } catch (Exception e) {
            throw new ServiceUnvailableException(SERVICE_UNAVAILABLE);
        }

    }

    public boolean isCurrencyAvailable(final String currencyCode) {
        String error = String.format("Currency or service not available %s", currencyCode);
        try {
            Monetary.getCurrency(currencyCode);
            return true;
        } catch (MonetaryException e) {
            throw new BadParameterException(error);
        } catch (Exception e) {
            throw new ServiceUnvailableException(error);
        }
    }

    public MonetaryAmount convertAmount(final String fromCurrency, final String toCurrency, final Double fromAmount) {
        String error = String.format("Conversion failed or service not available %s to %S", fromCurrency, toCurrency);
        try {
            Money fromAmountWithCurr = Money.of(fromAmount, fromCurrency);
            CurrencyConversion conversion = getCurrencyRateConversion(toCurrency);
            // Perform conversion using exchange rates
            MonetaryAmount convertedAmount = fromAmountWithCurr.with(conversion);
            return convertedAmount;
        } catch (MonetaryException e) {
            throw new BadParameterException(error);
        } catch (Exception e) {
            throw new ServiceUnvailableException(error);
        }
    }

}
