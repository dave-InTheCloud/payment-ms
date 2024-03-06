package lu.dave.finance.payment.service;


import javax.money.convert.ExchangeRate;

public interface ExchangeService {

    // change to void an throw exception
    boolean isCurrencyAvailable(final String currencyCode);

    ExchangeRate getCurrencyRate(final String fromCurrency, final String toCurrency);
    Double convertAmount(final String fromCurrency, final String toCurrency, final Double fromAmount);
}
