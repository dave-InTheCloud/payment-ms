package lu.dave.finance.payment.exception;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
public class ServiceUnvailableException extends RuntimeException {
    private String message;
    public ServiceUnvailableException(String message) {
        super(message);
        this.message = message;
    }
}
