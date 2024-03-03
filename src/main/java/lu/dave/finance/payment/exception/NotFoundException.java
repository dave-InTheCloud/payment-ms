package lu.dave.finance.payment.exception;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {
    public static final String NO_ENTITY = "No %s found with the id %s";
    private String message;
    private String entity;
    private Long id;
    public NotFoundException(String entity, Long id) {
        super(String.format(NO_ENTITY, entity, id.toString()));
        this.message = String.format(NO_ENTITY, entity, id.toString());
        this.entity = entity;
        this.id = id;
    }
}
