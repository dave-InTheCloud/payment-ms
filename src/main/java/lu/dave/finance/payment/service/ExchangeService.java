package lu.dave.finance.payment.service;


import javax.money.convert.CurrencyConversion;

public interface ExchangeService {

    // change to void an throw exception
    boolean isCurrencyAvailable(String currencyCode);
    CurrencyConversion getCurrencyRate(String currency);
    double convertAmount(String fromCurrency, double fromAmount, String toCurrency);

}
