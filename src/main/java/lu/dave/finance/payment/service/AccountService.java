package lu.dave.finance.payment.service;

import lu.dave.finance.payment.dto.AccountDto;
import lu.dave.finance.payment.dto.AccountDtoWithCustomer;
import lu.dave.finance.payment.entity.AccountEntity;

import java.util.List;

public interface AccountService {
    AccountDtoWithCustomer save(AccountDto accountDto);
    AccountEntity save(final AccountEntity accountEntity);
    AccountEntity findById(Long id);

    List<AccountDtoWithCustomer> findAll();
}
