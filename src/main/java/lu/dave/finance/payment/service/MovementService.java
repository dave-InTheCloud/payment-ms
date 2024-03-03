package lu.dave.finance.payment.service;

import lu.dave.finance.payment.dto.AccountDto;
import lu.dave.finance.payment.dto.AccountDtoWithCustomer;
import lu.dave.finance.payment.dto.MovementDto;
import lu.dave.finance.payment.entity.AccountEntity;

import java.util.List;

public interface MovementService {
    MovementDto save(MovementDto dto);
}
