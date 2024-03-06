package lu.dave.finance.payment.dto;


import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
@JsonPropertyOrder({"id", "movementType", "status" , "fromCurrency", "toCurrent"})
public class MovementDtoPageable {
    List<MovementDto> movements;
    PageableDto page;
}
