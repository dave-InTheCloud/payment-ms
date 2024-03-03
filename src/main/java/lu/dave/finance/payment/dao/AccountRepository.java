package lu.dave.finance.payment.dao;

import lu.dave.finance.payment.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface AccountRepository extends PagingAndSortingRepository<AccountEntity, Long>
        , ListCrudRepository<AccountEntity, Long>, JpaRepository<AccountEntity, Long> {

    // Add additional custom query methods here if needed
    boolean existsAccountEntityBySerialNumber(String name);
}