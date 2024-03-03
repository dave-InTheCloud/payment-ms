package lu.dave.finance.payment.exception;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class ForbiddenException extends RuntimeException {
    private String message;
    public ForbiddenException(String message) {
        this.message = message;
    }

}
