package lu.dave.finance.payment.dto;


import lombok.Data;
import lu.dave.finance.payment.entity.enumaration.MovementType;

@Data
public class MovementDto extends  MovementDtoRequest {
    private final MovementType movementType;

    private final AccountDto fromAccount;

    private final AccountDto toAccount;

}
