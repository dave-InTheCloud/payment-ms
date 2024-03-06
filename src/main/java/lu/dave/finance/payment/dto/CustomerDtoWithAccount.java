package lu.dave.finance.payment.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class CustomerDtoWithAccount extends CustomerDto {
        private List<AccountDto> accounts;
}
