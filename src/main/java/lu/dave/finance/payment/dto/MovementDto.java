package lu.dave.finance.payment.dto;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MovementDto extends MovementDtoRequest {
    private long id;
    private String movementType;
    private String status;
    private String currency;
    private LocalDateTime orderedOn;
}
