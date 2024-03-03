package lu.dave.finance.payment.service;


import javax.money.convert.CurrencyConversion;

public interface ExchangeService {

    // change to void an throw exception
    boolean isCurrencyAvailable(final String currencyCode);
    CurrencyConversion getCurrencyRate(final String currency);
    double convertAmount(final String fromCurrency,final String toCurrency,final Double fromAmount);

}
