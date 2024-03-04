package lu.dave.finance.payment.dto;


import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@Data
@JsonPropertyOrder({"id", "movementType", "status" , "fromCurrency", "toCurrent"})
public class MovementDtoCreated extends MovementDtoRequest {
    private long id;
    private String movementType;
    private String status;
    private String fromCurrency;
    private String toCurrency;
    private Double convertedAmount;
}
