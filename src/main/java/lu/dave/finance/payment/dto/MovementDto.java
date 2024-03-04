package lu.dave.finance.payment.dto;


import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@Data
public class MovementDto extends MovementDtoRequest {
    private long id;
    private String movementType;
    private String status;
    private String currency;
}
