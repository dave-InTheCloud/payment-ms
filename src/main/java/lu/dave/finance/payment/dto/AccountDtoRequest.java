package lu.dave.finance.payment.dto;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.Data;
import lu.dave.finance.payment.entity.enumaration.AccountType;

@Data
public class AccountDtoRequest {

    @Min(1L)
    private Long parentId;

    @Size(min = 3, max = 30)
    private String name;

    // IBAN not ok for US
    //@Pattern(regexp = "^[A-Z]{2}[0-9]{2}[A-Z0-9]{1,32}$")
    @Size(min = 5, max = 50)
    private String serialNumber;

    @Enumerated(EnumType.STRING)
    private AccountType type;

    @NotNull(message = "Your Account needs a owner id.")
    @Min(value = 1L)
    private Long ownerId;

    @NotBlank(message = "currencyCode should be present ")
    @Size(min = 3, max = 3)
    private String currencyCode;

    @NotNull
    @DecimalMin("0.01")
    private Double balance;
}
