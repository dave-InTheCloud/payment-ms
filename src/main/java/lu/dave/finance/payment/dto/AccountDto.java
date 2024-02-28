package lu.dave.finance.payment.dto;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lu.dave.finance.payment.entity.enumaration.AmountType;

@Data
public class AccountDto {

        private Long id;

        @NotNull
        @Min(1)
        private long customerId;

        @Enumerated(EnumType.STRING)
        private AmountType balanceType;

        @NotBlank(message = "currency should  present and follow this regular expression .+[@].+[\\.].+. ")
        @Size(min = 3, max = 3)
        private String currencyCode;

        @DecimalMin("0.0")
        private Double balance = 0.0;
}
