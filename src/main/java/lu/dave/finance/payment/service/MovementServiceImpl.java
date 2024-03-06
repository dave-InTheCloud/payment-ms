package lu.dave.finance.payment.service;

import lombok.AllArgsConstructor;
import lu.dave.finance.payment.dao.MovementRepository;
import lu.dave.finance.payment.dto.MovementDtoCreated;
import lu.dave.finance.payment.dto.MovementDtoPageable;
import lu.dave.finance.payment.dto.MovementDtoRequest;
import lu.dave.finance.payment.dto.PageableDto;
import lu.dave.finance.payment.entity.AccountEntity;
import lu.dave.finance.payment.entity.MovementEntity;
import lu.dave.finance.payment.entity.enumaration.MovementStatus;
import lu.dave.finance.payment.entity.enumaration.MovementType;
import lu.dave.finance.payment.exception.BadParameterException;
import lu.dave.finance.payment.exception.NotFoundException;
import lu.dave.finance.payment.mapper.MovementMapper;
import lu.dave.finance.payment.util.PageValidationUtil;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@Transactional(readOnly = true)
@AllArgsConstructor
@Service
public class MovementServiceImpl implements MovementService {
    private final MovementRepository movementRepository;
    private final AccountServiceImpl accountServiceImpl;
    private final ExchangeService exchangeServiceImpl;
    private final ConversionService conversionService;
    private final MovementMapper movementMapper;


    public MovementDtoPageable getAll(final Pageable pageable) {
        final Page<MovementEntity> movementEntities = movementRepository.findAll(pageable);
        PageValidationUtil.validatePageNumber(movementEntities, pageable);

        return new MovementDtoPageable(movementMapper.entityToDto(movementEntities.getContent()),
                new PageableDto(movementEntities));
    }
    public MovementDtoPageable getMovementsByAccountId(final Pageable pageable, final Long id) {
        if(!accountServiceImpl.existById(id)) throw new NotFoundException("Account", id);
        final Page<MovementEntity> movementEntities = movementRepository.findByAccountId(id, pageable);
        PageValidationUtil.validatePageNumber(movementEntities, pageable);

        return new MovementDtoPageable(movementMapper.entityToDto(movementEntities.getContent()),
                new PageableDto(movementEntities));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public MovementDtoCreated save(MovementDtoRequest dto) {
        final AccountEntity fromAccount = dto.getFromAccountId() != null ?
                accountServiceImpl.findById(dto.getFromAccountId()) :
                accountServiceImpl.findBySerialNumber(dto.getFromSerialNumber());

        AccountEntity toAccount = dto.getToAccountId() != null ?
                accountServiceImpl.findById(dto.getToAccountId())
                : accountServiceImpl.findBySerialNumber(dto.getToSerialNumber());

        Double convertedAmount = exchangeServiceImpl.convertAmount(fromAccount.getCurrencyCode(),
                toAccount.getCurrencyCode(), dto.getAmount());

        return this.executeTransaction(dto, fromAccount, toAccount, convertedAmount);
    }


    private MovementDtoCreated save(final MovementDtoRequest dto, final AccountEntity accountEntity,
                                    final MovementType movementType, AccountEntity toAccount, LocalDateTime orderedOn) {
        MovementEntity movement = conversionService.convert(dto, MovementEntity.class);
        movement.setMovementType(movementType);
        movement.setStatus(MovementStatus.PENDING);
        movement.setOrderedOn(orderedOn);

        if (MovementType.DEBIT == movementType) {
            movement.setCurrencyCode(accountEntity.getCurrencyCode());
            movement.setAccount(accountEntity);
            movement.setToAccount(toAccount);
        } else {
            movement.setCurrencyCode(toAccount.getCurrencyCode());
            movement.setAccount(toAccount);
            movement.setToAccount(accountEntity);
        }

        final MovementDtoCreated res = movementMapper.convert(movementRepository.save(movement));
        res.setFromAccountId(accountEntity.getId());
        res.setFromSerialNumber(accountEntity.getSerialNumber());
        res.setFromCurrency(accountEntity.getCurrencyCode());
        res.setToCurrency(toAccount.getCurrencyCode());
        res.setToAccountId(toAccount.getId());
        res.setToSerialNumber(toAccount.getSerialNumber());

        return res;
    }

    private MovementDtoCreated executeTransaction(final MovementDtoRequest movementDtoReq, final AccountEntity fromAccount,
                                                  AccountEntity toAccount, final Double convertedAmount) {
        checkTransactionRules(fromAccount, toAccount, convertedAmount);

        fromAccount.setBalance(fromAccount.getBalance() - movementDtoReq.getAmount());
        toAccount.setBalance(toAccount.getBalance() + convertedAmount);

        accountServiceImpl.save(fromAccount);
        accountServiceImpl.save(toAccount);

        final LocalDateTime orderedOn = LocalDateTime.now();

        final MovementDtoCreated fromMovement = this.save(movementDtoReq, fromAccount, MovementType.DEBIT, toAccount, orderedOn);

        //will be use in save moment to store converted amount ond DB
        movementDtoReq.setAmount(convertedAmount);
        this.save(movementDtoReq, fromAccount, MovementType.CREDIT, toAccount, orderedOn);

        fromMovement.setConvertedAmount(convertedAmount);

        return fromMovement;
    }


    private static void checkTransactionRules(final AccountEntity fromAccount, final AccountEntity toAccount, final Double amount) {
        if (fromAccount.getId().equals(toAccount.getId()))
            throw new BadParameterException("You cannot transfer fund from the same account");

        final String fundOverMsg = "Not enough fund the balance is %s and amount is %s";
        if (fromAccount.getBalance() < amount)
            throw new BadParameterException(String.format(fundOverMsg, fromAccount.getBalance(), amount));

    }


}
