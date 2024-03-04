package lu.dave.finance.payment.exception;

//@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadParameterException extends RuntimeException {
    private String message;
    public BadParameterException(String message) {
        super(message);
    }
}
