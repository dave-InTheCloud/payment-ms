package lu.dave.finance.payment.dto;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
@Data
public class MovementDtoRequest {
    private Long  fromAccountId;
    private String  fromSerialNumber;
    private Long  toAccountId;
    private String  toSerialNumber;
    @NotNull
    @Min(0L)
    private Double amount;

}
