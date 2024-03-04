package lu.dave.finance.payment.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
public class AccountDtoWithChildren extends AccountDto {

    private Long id;

    private CustomerDto customer;

    private AccountDto parent;

    private List<AccountDto> children;
}
