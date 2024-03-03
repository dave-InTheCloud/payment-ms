package lu.dave.finance.payment.service;

import lombok.AllArgsConstructor;
import lu.dave.finance.payment.dao.AccountRepository;
import lu.dave.finance.payment.dao.ContributorRepository;
import lu.dave.finance.payment.dao.CustomerRepository;
import lu.dave.finance.payment.entity.AccountEntity;
import lu.dave.finance.payment.entity.ContributorEntity;
import lu.dave.finance.payment.entity.ContributorId;
import lu.dave.finance.payment.entity.CustomerEntity;
import lu.dave.finance.payment.entity.enumaration.ContributorType;
import lu.dave.finance.payment.exception.BadParameterException;
import lu.dave.finance.payment.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import static lu.dave.finance.payment.exception.NotFoundException.NO_ENTITY;

@AllArgsConstructor
@Service
public class ContributorServiceImpl implements ContributorService {

    private final ContributorRepository contributorRepository;
    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;

    public ContributorEntity save(ContributorType contributorType, AccountEntity account, Long customerId, Double amount) {
        final CustomerEntity customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException("customer", customerId));

        final ContributorId contributorId = new ContributorId(account.getId(), customerId);
        ContributorEntity contributor = new ContributorEntity();
        contributor.setContributor(customer);
        contributor.setAccount(account);
        contributor.setContributorId(contributorId);
        contributor.setAmount(amount);
        contributor.setType(contributorType);

        return contributorRepository.save(contributor);
    }

    //
    public ContributorEntity save(ContributorType contributorType, Long accountId, Long customerId) {
        final AccountEntity account = accountRepository.findById(accountId)
                .orElseThrow(() -> new NotFoundException("account", accountId));

        if (contributorType == ContributorType.OWNER &&
                (CollectionUtils.isEmpty(account.getContributors()) || account.getContributors().size() == 1)) {
            return this.save(contributorType, account, customerId, account.getBalance());
        } else {
            throw new BadParameterException("The account has an Owner or your are already  the owner");
        }
    }


    public ContributorEntity findById(Long id) {
        return contributorRepository.findById(id).orElseThrow(() -> new NotFoundException("contributor", id));
    }

}
