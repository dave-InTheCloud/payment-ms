package lu.dave.finance.payment.service;

import lombok.AllArgsConstructor;
import lu.dave.finance.payment.dao.AccountRepository;
import lu.dave.finance.payment.dao.ContributorRepository;
import lu.dave.finance.payment.dao.CustomerRepository;
import lu.dave.finance.payment.entity.ContributorEntity;
import lu.dave.finance.payment.entity.AccountEntity;
import lu.dave.finance.payment.entity.ContributorId;
import lu.dave.finance.payment.entity.CustomerEntity;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ContributorServiceImpl implements ContributorService {

    private final ContributorRepository contributorRepository;
    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;

    public ContributorEntity save(Long accountId, Long customerId) {
        final CustomerEntity customer = customerRepository.findById(customerId).orElseThrow();
        final AccountEntity account = accountRepository.findById(accountId).orElseThrow();
        final ContributorId contributorId = new ContributorId(accountId, customerId);
        ContributorEntity contributor = new ContributorEntity();
        contributor.setContributor(customer);
        contributor.setAccount(account);
        contributor.setContributorId(contributorId);
        contributor.setAmount(0.0);

        return contributorRepository.save(contributor);

        // new AccountContributorEntity();

    }

}
