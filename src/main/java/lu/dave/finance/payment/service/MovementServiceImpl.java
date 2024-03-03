package lu.dave.finance.payment.service;

import lombok.AllArgsConstructor;
import lu.dave.finance.payment.dao.AccountRepository;
import lu.dave.finance.payment.dao.MovementRepository;
import lu.dave.finance.payment.dto.AccountDto;
import lu.dave.finance.payment.dto.MovementDto;
import lu.dave.finance.payment.entity.AccountEntity;
import lu.dave.finance.payment.entity.ContributorEntity;
import lu.dave.finance.payment.entity.MovementEntity;
import lu.dave.finance.payment.entity.enumaration.AmountType;
import lu.dave.finance.payment.entity.enumaration.ContributorType;
import lu.dave.finance.payment.exception.BadParameterException;
import lu.dave.finance.payment.exception.ForbiddenException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static lu.dave.finance.payment.service.AccountServiceImpl.WRONG_PRIVILEGE;

@AllArgsConstructor
@Service
public class MovementServiceImpl implements MovementService {

    private final MovementRepository movementRepository;
    private final AccountServiceImpl accountServiceImpl;
    private final ContributorService contributorServiceImpl;

    private final ExchangeService exchangeServiceImpl;

    public MovementDto save(MovementDto dto) {
        final AccountEntity fromAccount = accountServiceImpl.findById(dto.getFromAccountId());
        final AccountEntity toAccount = accountServiceImpl.findById(dto.getToAccoutId());
        this.save(dto, fromAccount, toAccount);
        this.transaction(dto);

        return null;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    private MovementDto save(MovementDto dto, AccountEntity fromAccount, AccountEntity toAccount) {
        MovementEntity movement = new MovementEntity();
        movement.setAmount(dto.getAmount());
        movement.setAmountType(amountType(dto.getAmount()));
        movement.setFromAccount(fromAccount);
        movement.setToAccount(toAccount);
        movement.setCustomerId(dto.getToCustomerId());


        movementRepository.save(movement);

        return null;
    }

    public void transaction(MovementDto movementDto) {
        this.transaction(movementDto.getFromAccountId(), movementDto.getToAccoutId(), movementDto.getAmount());
    }

    private void transaction(Long fromAccountId, Long toAccountId, Double amount) {
        this.transaction(accountServiceImpl.findById(fromAccountId).getCustomer().getId(), fromAccountId, toAccountId, amount);
    }

    private void transaction(Long customerId, Long fromAccountId, Long toAccountId, Double amount) {
        ContributorEntity contributor = contributorServiceImpl.findById(customerId);
        this.transaction(contributor, fromAccountId, toAccountId, amount);
    }

    public void transaction(final ContributorEntity contributor, Long fromAccountId, Long toAccountId, Double amount) {
        // If authentication is done role and id should be retrieved from there and filter with secured annotation
        AccountEntity fromAccount = accountServiceImpl.findById(fromAccountId);
        AccountEntity toAccount = accountServiceImpl.findById(toAccountId);

        CheckTransactionRules(contributor, fromAccount, amount);

        Double amountConverted = exchangeServiceImpl.convertAmount(fromAccount.getCurrencyCode(),
                toAccount.getCurrencyCode(), amount);

        fromAccount.setBalance(fromAccount.getBalance() - amount);
        toAccount.setBalance(toAccount.getBalance() - amountConverted);


        accountServiceImpl.save(fromAccount);
        accountServiceImpl.save(toAccount);
    }

    private static void CheckTransactionRules(ContributorEntity contributor, AccountEntity fromAccount, Double amount) {
        if (contributor.getType() == ContributorType.VIEWER) throw new ForbiddenException(WRONG_PRIVILEGE);

        boolean isOwner = contributor.getType() == ContributorType.OWNER && fromAccount.getBalance() < amount;
        boolean isParticipant = contributor.getType() == ContributorType.PARTICIPANT && contributor.getAmount() < amount;

        final String fundOverMsg = "Not enough fund the balance is %s and amount is %s";

        if (isOwner) throw new BadParameterException(String.format(fundOverMsg, fromAccount.getBalance(), amount));
        if (isParticipant) throw new BadParameterException(String.format(fundOverMsg, contributor.getAmount(), amount));
    }

    public AmountType amountType(Double amount) {
        return amount < 0 ? AmountType.CREDIT : AmountType.DEBIT;
    }
}
