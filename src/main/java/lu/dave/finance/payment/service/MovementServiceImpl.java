package lu.dave.finance.payment.service;

import lombok.AllArgsConstructor;
import lu.dave.finance.payment.dao.AccountRepository;
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

import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class MovementServiceImpl implements MovementService {

    private final MovementRepository movementRepository;
    private final AccountServiceImpl accountServiceImpl;
    private final ExchangeService exchangeServiceImpl;

    private final ConversionService conversionService;

    private final MovementMapper movementMapper;

    private final AccountRepository accountRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public MovementDto save(MovementDtoRequest dto) {
        final AccountEntity fromAccount = accountServiceImpl.findById(dto.getFromAccountId());

        this.transaction(dto);
        this.save(dto, fromAccount, MovementType.DEBIT, MovementStatus.PENDING);
        return this.save(dto, fromAccount, MovementType.CREDIT, MovementStatus.PENDING);
    }


    private MovementDto save(MovementDtoRequest dto, AccountEntity account, MovementType movementType, MovementStatus status) {
        MovementEntity movement = conversionService.convert(dto, MovementEntity.class);

        movement.setAccount(account);
        movement.setMovementType(movementType);
        movement.setStatus(status);

        movementRepository.save(movement);

        return movementMapper.convertToDto(dto);
    }

    public void transaction(final MovementDtoRequest movementDto) {
        this.transaction(movementDto.getFromAccountId(), movementDto.getToAccountId(), movementDto.getAmount());
    }

    public void transaction(final Long fromAccountId, final Long toAccountId, final Double amount) {
        // If authentication is done role and id should be retrieved from there and filter with secured annotation
        AccountEntity fromAccount = accountServiceImpl.findById(fromAccountId);
        AccountEntity toAccount = accountServiceImpl.findById(toAccountId);

        CheckTransactionRules(fromAccount, toAccount, amount);

        Double amountConverted = exchangeServiceImpl.convertAmount(fromAccount.getCurrencyCode(),
                toAccount.getCurrencyCode(), amount).getNumber().doubleValue();

        boolean isParents = accountServiceImpl.findAllParents(fromAccountId)
                .stream().filter(id -> id.equals(toAccount)).count() > 0;

        boolean isChild = accountServiceImpl.findAllChildren(fromAccountId)
                .stream().filter(id -> id.equals(toAccount)).count() > 0;

        // Same parents no needs to update parents account
        if (fromAccount.getParent().getId().equals(toAccount.getParent().getId())) {
            fromAccount.setBalance(fromAccount.getBalance() - amount);
            toAccount.setBalance(toAccount.getBalance() + amountConverted);

            accountServiceImpl.save(fromAccount);
            accountServiceImpl.save(toAccount);
        } else if (isParents || isChild) {
            throw new BadParameterException("Transfer between account on the same hierarchy at a different level is not allowed");
        } else {
            accountServiceImpl.save(fromAccount, amount, MovementType.DEBIT);
            accountServiceImpl.save(toAccount, amountConverted, MovementType.CREDIT);
        }
    }


    private static void CheckTransactionRules(AccountEntity fromAccount, AccountEntity toAccount, Double amount) {
        if (fromAccount.getId().equals(toAccount.getId()))
            throw new BadParameterException("You cannot transfer fund from the same account");

        final String fundOverMsg = "Not enough fund the balance is %s and amount is %s";
        if (fromAccount.getBalance() < amount)
            throw new BadParameterException(String.format(fundOverMsg, fromAccount.getBalance(), amount));

    }


}
