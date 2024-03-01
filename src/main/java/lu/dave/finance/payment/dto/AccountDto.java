package lu.dave.finance.payment.dto;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class AccountDto {

    private Long id;

    @NotBlank(message = "Your Account needs a name.")
    @Size(min = 3, max = 30)
    private String name;

    @NotNull(message = "Your Account needs a owner id.")
   // @Min(1)
    @Min(value = 1L)
    private Long ownerId;

    @NotBlank(message = "currencyCode should be present ")
    @Size(min = 3, max = 3)
    private String currencyCode;

    @DecimalMin("0.0")
    private Double balance = 0.0;
}
