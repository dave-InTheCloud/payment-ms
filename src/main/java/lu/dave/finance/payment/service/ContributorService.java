package lu.dave.finance.payment.service;

import lu.dave.finance.payment.entity.ContributorEntity;

public interface ContributorService {

    ContributorEntity save(Long accountId, Long customerId);
}
