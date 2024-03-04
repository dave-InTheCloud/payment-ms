package lu.dave.finance.payment.service;

import lu.dave.finance.payment.dto.AccountDto;
import lu.dave.finance.payment.dto.AccountDtoRequest;
import lu.dave.finance.payment.dto.AccountDtoWithCustomer;
import lu.dave.finance.payment.entity.AccountEntity;

import java.util.List;

public interface AccountService {
    AccountDto save(AccountDtoRequest accountDtoRequest);
    AccountEntity save(final AccountEntity accountEntity);
    AccountEntity findById(Long id);
    AccountDtoWithCustomer getById(Long id);

    AccountDtoWithCustomer getBySerialNumber(String ssn);

    List<? extends AccountDto> findAll();

    //List<MovementDto>
}
