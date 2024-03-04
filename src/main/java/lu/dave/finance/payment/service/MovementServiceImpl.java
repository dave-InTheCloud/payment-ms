package lu.dave.finance.payment.service;

import lombok.AllArgsConstructor;
import lu.dave.finance.payment.dao.MovementRepository;
import lu.dave.finance.payment.dto.MovementDto;
import lu.dave.finance.payment.dto.MovementDtoRequest;
import lu.dave.finance.payment.entity.AccountEntity;
import lu.dave.finance.payment.entity.MovementEntity;
import lu.dave.finance.payment.entity.enumaration.MovementStatus;
import lu.dave.finance.payment.entity.enumaration.MovementType;
import lu.dave.finance.payment.exception.BadParameterException;
import lu.dave.finance.payment.mapper.MovementMapper;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class MovementServiceImpl implements MovementService {

    private final MovementRepository movementRepository;
    private final AccountServiceImpl accountServiceImpl;
    private final ExchangeService exchangeServiceImpl;

    private final ConversionService conversionService;
    private final MovementMapper movementMapper;


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public MovementDto save(MovementDtoRequest dto) {
        final AccountEntity fromAccount = dto.getFromAccountId() != null ?
                accountServiceImpl.findById(dto.getFromAccountId()) :
                accountServiceImpl.findBySerialNumber(dto.getFromSerialNumber());

        this.transaction(dto, fromAccount);
        this.save(dto, fromAccount, MovementType.DEBIT);
        return this.save(dto, fromAccount, MovementType.CREDIT);
    }


    private MovementDto save(final MovementDtoRequest dto, final AccountEntity account,
                             final MovementType movementType) {
        MovementEntity movement = conversionService.convert(dto, MovementEntity.class);

        movement.setAccount(account);
        movement.setMovementType(movementType);
        movement.setStatus(MovementStatus.PENDING);

        movementRepository.save(movement);

        return movementMapper.convertToDto(dto);
    }

    public void transaction(final MovementDtoRequest movementDto, final AccountEntity fromAccount) {
        // If authentication is done role and id should be retrieved from there and filter with secured annotation
        //AccountEntity fromAccount = accountServiceImpl.findById(fromAccountId);
        AccountEntity toAccount = movementDto.getToAccountId() != null ?
                accountServiceImpl.findById(movementDto.getToAccountId())
                : accountServiceImpl.findBySerialNumber(movementDto.getToSerialNumber());

        CheckTransactionRules(fromAccount, toAccount, movementDto.getAmount());

        Double amountConverted = exchangeServiceImpl.convertAmount(fromAccount.getCurrencyCode(),
                toAccount.getCurrencyCode(), movementDto.getAmount()).getNumber().doubleValue();

        fromAccount.setBalance(fromAccount.getBalance() - movementDto.getAmount());
        toAccount.setBalance(toAccount.getBalance() + amountConverted);

        accountServiceImpl.save(fromAccount);
        accountServiceImpl.save(toAccount);
    }


    private static void CheckTransactionRules(final AccountEntity fromAccount, final AccountEntity toAccount, final Double amount) {
        if (fromAccount.getId().equals(toAccount.getId()))
            throw new BadParameterException("You cannot transfer fund from the same account");

        final String fundOverMsg = "Not enough fund the balance is %s and amount is %s";
        if (fromAccount.getBalance() < amount)
            throw new BadParameterException(String.format(fundOverMsg, fromAccount.getBalance(), amount));

    }


}
