package lu.dave.finance.payment.dto;


import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
@Data
@JsonPropertyOrder({"id", "movementType", "status" , "fromCurrency", "currency", "toCurrency", "fromAccountId", "toAccountId",
        "fromSerialNumber", "toSerialNumber","amount"})
public class MovementDtoRequest {
    private Long  fromAccountId;
    private String  fromSerialNumber;
    private Long  toAccountId;
    private String  toSerialNumber;
    @NotNull
    @Min(0L)
    private Double amount;

}
