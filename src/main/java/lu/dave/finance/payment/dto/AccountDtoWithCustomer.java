package lu.dave.finance.payment.dto;


import lombok.Data;

import java.util.List;

@Data
public class AccountDtoWithCustomer extends  AccountDto {

    private CustomerDto customer;

    private List<ContributorDto> contributors;

}
