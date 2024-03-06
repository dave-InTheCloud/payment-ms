package lu.dave.finance.payment.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class AccountDtoPageable {
    List<? extends AccountDto> accounts;
    PageableDto page;
}
