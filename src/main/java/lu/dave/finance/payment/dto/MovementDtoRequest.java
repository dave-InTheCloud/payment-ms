package lu.dave.finance.payment.dto;


import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
@Data
@JsonPropertyOrder({"id", "movementType", "status" , "fromCurrency", "currency", "toCurrency", "fromAccountId", "toAccountId",
        "fromSerialNumber", "toSerialNumber","amount"})
public class MovementDtoRequest {
    @Min(1L)
    private Long  fromAccountId;
    private String  fromSerialNumber;
    @Min(1L)
    private Long  toAccountId;
    private String  toSerialNumber;
    @NotNull
    @DecimalMin("0.01")
    private Double amount;

}
