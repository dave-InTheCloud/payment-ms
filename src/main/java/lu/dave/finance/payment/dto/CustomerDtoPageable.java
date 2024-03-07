package lu.dave.finance.payment.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class CustomerDtoPageable {
    List<CustomerDtoWithAccount> customers;
    PageableDto page;
}
