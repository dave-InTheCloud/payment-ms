package lu.dave.finance.payment.service;


public interface CurrencyService {
    double convertAmount(String fromCurrency, double fromAmount, String toCurrency);

}
