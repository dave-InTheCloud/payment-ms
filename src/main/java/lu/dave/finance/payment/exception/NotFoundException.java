package lu.dave.finance.payment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {
    public static final String NO_ENTITY = "No %s found with the id %s";
    private final String message;
    private final String entity;
    private final String id;
    public NotFoundException(String entity, Long id) {
        super(String.format(NO_ENTITY, entity, id.toString()));
        this.id = id.toString();
        this.message = String.format(NO_ENTITY, entity, id);
        this.entity = entity;

    }
    public NotFoundException(String entity, String id){
        super(String.format(NO_ENTITY, entity, id));
        this.message = String.format(NO_ENTITY, entity, id);
        this.entity = entity;
        this.id = id;
    }
}
