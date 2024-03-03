package lu.dave.finance.payment.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lu.dave.finance.payment.dao.AccountRepository;
import lu.dave.finance.payment.dto.AccountDto;
import lu.dave.finance.payment.dto.AccountDtoWithCustomer;
import lu.dave.finance.payment.dto.MovementDto;
import lu.dave.finance.payment.entity.AccountEntity;
import lu.dave.finance.payment.entity.ContributorEntity;
import lu.dave.finance.payment.entity.CustomerEntity;
import lu.dave.finance.payment.entity.enumaration.AccountType;
import lu.dave.finance.payment.entity.enumaration.ContributorType;
import lu.dave.finance.payment.exception.BadParameterException;
import lu.dave.finance.payment.exception.ForbiddenException;
import lu.dave.finance.payment.exception.NotFoundException;
import lu.dave.finance.payment.mapper.AccountMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@Slf4j
@AllArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {
    public static final String WRONG_PRIVILEGE = "Not enough privilege for this operation";
    private final AccountRepository accountRepository;
    private final ConversionService conversionService;
    private final ExchangeService exchangeServiceImpl;
    private final CustomerService customerServiceImpl;
    private final ContributorService contributorServiceImpl;
    private final AccountMapper accountMapperImpl;

    private final MovementService movementServiceImpl;

    @Override
    public List<AccountDtoWithCustomer> findAll() {
        return accountMapperImpl.convert(accountRepository.findAll());
    }

    public AccountEntity findById(Long id) {
        return accountRepository.findById(id).orElseThrow(() -> new NotFoundException("account", id));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public AccountEntity save(final AccountEntity accountEntity) {
        return accountRepository.save(accountEntity);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public AccountDtoWithCustomer save(final AccountDto accountDto) {
        exchangeServiceImpl.isCurrencyAvailable(accountDto.getCurrencyCode());
        checkAdditionalRules(accountDto);

        final CustomerEntity customer = customerServiceImpl.findById(accountDto.getOwnerId());
        AccountEntity account = conversionService.convert(accountDto, AccountEntity.class);
        account.setCustomer(customer);

        account = accountRepository.save(account);
        final ContributorEntity contributor = contributorServiceImpl.save(ContributorType.OWNER, account.getId(), customer.getId());
        account.setContributors(List.of(contributor));

        return accountMapperImpl.convert(account);
    }

    private void checkAdditionalRules(final AccountDto accountDto) {
        boolean emptySinceRequest = StringUtils.isEmpty(accountDto.getSerialNumber());
        if (emptySinceRequest) generateSerialNumber(accountDto, emptySinceRequest);

        if (accountRepository.existsAccountEntityBySerialNumber(accountDto.getSerialNumber())) {
            throw new BadParameterException(
                    String.format("An account with the following serial number '%s' already exists.", accountDto.getName()));
        }
        if (accountDto.getType() == null) accountDto.setType(AccountType.SAVING);
        if (StringUtils.isEmpty(accountDto.getName())) accountDto.setName(accountDto.getType().name());
    }

    public void generateSerialNumber(AccountDto accountDto, boolean emptySinceRequest) {
        boolean exist = accountRepository.existsAccountEntityBySerialNumber(accountDto.getSerialNumber());
        if (emptySinceRequest && exist) {
            this.generateSerialNumber(accountDto, emptySinceRequest);
        } else if (emptySinceRequest) {
            String countryCode = StringUtils.chop(accountDto.getCurrencyCode());
            String sn = StringUtils.chop(countryCode) + RandomStringUtils.randomAlphanumeric(20);
            accountDto.setSerialNumber(sn);
        } else {
            throw new BadParameterException("The serialNumber already exist");
        }
    }

}
