package lu.dave.finance.payment.exception;

import lombok.AllArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadParameterException extends RuntimeException {
    private String message;
    public BadParameterException(String message) {
        super(message);
    }
}
