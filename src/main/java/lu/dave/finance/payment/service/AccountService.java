package lu.dave.finance.payment.service;

import lu.dave.finance.payment.dto.AccountDto;
import lu.dave.finance.payment.dto.AccountDtoRequest;
import lu.dave.finance.payment.entity.AccountEntity;
import lu.dave.finance.payment.entity.enumaration.MovementType;

import java.util.List;

public interface AccountService {
    AccountDto save(AccountDtoRequest accountDtoRequest);

    AccountEntity save(final AccountEntity accountEntity);
    AccountEntity save(final AccountEntity accountEntity, Double amount, MovementType movementType);
    AccountEntity findById(Long id);

    List<? extends AccountDto> findAll();
}
