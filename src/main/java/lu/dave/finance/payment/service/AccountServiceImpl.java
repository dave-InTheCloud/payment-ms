package lu.dave.finance.payment.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lu.dave.finance.payment.dao.AccountRepository;
import lu.dave.finance.payment.dto.AccountDto;
import lu.dave.finance.payment.dto.AccountDtoRequest;
import lu.dave.finance.payment.dto.AccountDtoWithCustomer;
import lu.dave.finance.payment.entity.AccountEntity;
import lu.dave.finance.payment.entity.CustomerEntity;
import lu.dave.finance.payment.entity.enumaration.AccountType;
import lu.dave.finance.payment.entity.enumaration.MovementType;
import lu.dave.finance.payment.exception.BadParameterException;
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
    private final AccountRepository accountRepository;
    private final ConversionService conversionService;
    private final ExchangeService exchangeServiceImpl;
    private final CustomerService customerServiceImpl;
    private final AccountMapper accountMapperImpl;


    @Override
    public List<? extends AccountDto> findAll() {
        return accountMapperImpl.convertWithChildren(accountRepository.findAll());
    }

    public AccountEntity findById(Long id) {
        return accountRepository.findById(id).orElseThrow(() -> new NotFoundException("account", id));
    }

    public AccountDtoWithCustomer getById(Long id) {
        return accountMapperImpl.convertWithChildren(this.findById(id));
    }

    public AccountDtoWithCustomer getBySerialNumber(String ssn) {
        return accountMapperImpl.convertWithChildren(this.findBySerialNumber(ssn));
    }

    public AccountEntity findBySerialNumber(String serialNumber) {
        return accountRepository.findBySerialNumber(serialNumber)
                .orElseThrow(() -> new NotFoundException("account", serialNumber));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public AccountEntity save(final AccountEntity accountEntity) {
        return accountRepository.save(accountEntity);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public AccountDto save(final AccountDtoRequest accountDtoRequest) {
        exchangeServiceImpl.isCurrencyAvailable(accountDtoRequest.getCurrencyCode());
        checkAdditionalRules(accountDtoRequest);

        final CustomerEntity customer = customerServiceImpl.findById(accountDtoRequest.getOwnerId());
        AccountEntity account = conversionService.convert(accountDtoRequest, AccountEntity.class);
        account.setCustomer(customer);

        account = accountRepository.save(account);

        return accountMapperImpl.convertWithChildren(account);
    }

    private void checkAdditionalRules(final AccountDtoRequest accountDtoRequest) {
        boolean emptySinceRequest = StringUtils.isEmpty(accountDtoRequest.getSerialNumber());
        if (emptySinceRequest) generateSerialNumber(accountDtoRequest, emptySinceRequest);

        if (accountRepository.existsAccountEntityBySerialNumber(accountDtoRequest.getSerialNumber())) {
            throw new BadParameterException(
                    String.format("An account with the following serial number '%s' already exists.", accountDtoRequest.getSerialNumber()));
        }
        if (accountDtoRequest.getType() == null) accountDtoRequest.setType(AccountType.PORTFOLIO);
        if (StringUtils.isEmpty(accountDtoRequest.getName()))
            accountDtoRequest.setName(accountDtoRequest.getType().name());
    }

    public void generateSerialNumber(AccountDtoRequest accountDtoRequest, boolean emptySinceRequest) {
        boolean exist = accountRepository.existsAccountEntityBySerialNumber(accountDtoRequest.getSerialNumber());
        if (emptySinceRequest && exist) {
            this.generateSerialNumber(accountDtoRequest, emptySinceRequest);
        } else if (emptySinceRequest) {
            String countryCode = StringUtils.chop(accountDtoRequest.getCurrencyCode());
            String sn = countryCode + RandomStringUtils.randomAlphanumeric(20);
            accountDtoRequest.setSerialNumber(sn);
        } else {
            throw new BadParameterException("The serialNumber already exist");
        }
    }

}
