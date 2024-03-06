package lu.dave.finance.payment.dto;


import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class CustomerDtoPageable {
    List<? extends CustomerDto> customers;
    PageableDto page;
}
