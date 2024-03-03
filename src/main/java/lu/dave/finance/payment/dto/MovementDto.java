package lu.dave.finance.payment.dto;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.Data;
import lu.dave.finance.payment.entity.enumaration.AccountType;

@Data
public class MovementDto {
    private String  fromCustomerId;
    private Long  fromAccountId;
    private String  fromSerialNumber;
    private Long  toAccoutId;
    private Long  toSerialNumber;
    private Long  toCustomerId;
    private Double amount;

}
