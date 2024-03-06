package lu.dave.finance.payment.service;

import lu.dave.finance.payment.dto.AccountDto;
import lu.dave.finance.payment.dto.AccountDtoPageable;
import lu.dave.finance.payment.dto.AccountDtoRequest;
import lu.dave.finance.payment.dto.AccountDtoWithCustomer;
import lu.dave.finance.payment.entity.AccountEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AccountService {
    AccountDto save(AccountDtoRequest accountDtoRequest);
    AccountEntity save(final AccountEntity accountEntity);
    AccountEntity findById(Long id);
    AccountDtoWithCustomer getById(Long id);

    AccountDtoWithCustomer getBySerialNumber(String ssn);

    List<? extends AccountDto> findAll();


    AccountDtoPageable findAll(Pageable pageable);
    //List<MovementDto>
}
