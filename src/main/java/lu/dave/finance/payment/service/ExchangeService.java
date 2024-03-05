package lu.dave.finance.payment.service;


import javax.money.MonetaryAmount;
import javax.money.convert.CurrencyConversion;
import javax.money.convert.ExchangeRate;
import javax.money.convert.ExchangeRateProvider;

public interface ExchangeService {

    // change to void an throw exception
    boolean isCurrencyAvailable(final String currencyCode);

    CurrencyConversion getCurrencyRateConversion(final String currency);

    ExchangeRate getCurrencyRate(final String fromCurrency, final String toCurrency);
    MonetaryAmount convertAmount(final String fromCurrency, final String toCurrency, final Double fromAmount);
}
