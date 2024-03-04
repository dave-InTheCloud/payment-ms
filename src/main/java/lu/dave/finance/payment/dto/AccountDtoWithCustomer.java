package lu.dave.finance.payment.dto;


import lombok.Data;

@Data
public class AccountDtoWithCustomer extends AccountDto {

    private Long id;

    private CustomerDtoRequest customer;
}
