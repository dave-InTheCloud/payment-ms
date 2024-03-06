package lu.dave.finance.payment.exception;

public class ForbiddenException extends RuntimeException {
    private final String message;
    public ForbiddenException(String message) {
        this.message = message;
    }

}
