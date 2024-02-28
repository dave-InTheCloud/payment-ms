package lu.dave.finance.payment.service;

import lu.dave.finance.payment.dto.AccountDto;
import lu.dave.finance.payment.entity.AccountEntity;

public interface AccountService {
    AccountEntity save(AccountDto accountDto);
}
