package lu.dave.finance.payment.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lu.dave.finance.payment.dao.AccountRepository;
import lu.dave.finance.payment.dto.AccountDto;
import lu.dave.finance.payment.entity.AccountEntity;
import lu.dave.finance.payment.entity.ContributorEntity;
import lu.dave.finance.payment.entity.CustomerEntity;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final ConversionService conversionService;
    private final ExchangeService exchangeServiceImpl;
    private final CustomerService customerServiceImpl;

    private final ContributorService contributorServiceImpl;

    @Override
    @Transactional
    public AccountEntity save(AccountDto accountDto) {
        exchangeServiceImpl.isCurrencyAvailable(accountDto.getCurrencyCode());
        final CustomerEntity customer = customerServiceImpl.findById(accountDto.getOwnerId());
        AccountEntity account = conversionService.convert(accountDto, AccountEntity.class);
        account.setCustomer(customer);

        account = accountRepository.save(account);
        final ContributorEntity contributor = contributorServiceImpl.save(account.getId(), customer.getId());
        //account.setContributors(List.of(contributor));

        return account;
    }

    public AccountEntity findById(Long id) {
        return accountRepository.findById(id).orElseThrow();
    }

}
