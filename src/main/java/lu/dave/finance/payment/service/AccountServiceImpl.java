package lu.dave.finance.payment.service;

import lombok.AllArgsConstructor;
import lu.dave.finance.payment.dao.AccountRepository;
import lu.dave.finance.payment.dto.AccountDto;
import lu.dave.finance.payment.entity.AccountEntity;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
@AllArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {

    private final ConversionService conversionService;

    private final AccountRepository  accountRepository;

    @Override
    public AccountEntity save(AccountDto accountDto) {
        AccountEntity account =  conversionService.convert(accountDto, AccountEntity.class);
        return accountRepository.save(account);
    }
}
