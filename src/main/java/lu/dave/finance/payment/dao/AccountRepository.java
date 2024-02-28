package lu.dave.finance.payment.dao;

import lu.dave.finance.payment.entity.AccountEntity;
import lu.dave.finance.payment.entity.CustomerEntity;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends PagingAndSortingRepository<AccountEntity, Long>
        , ListCrudRepository<AccountEntity, Long> {

    // Add additional custom query methods here if needed

}