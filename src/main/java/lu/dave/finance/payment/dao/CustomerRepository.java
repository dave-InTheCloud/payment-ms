package lu.dave.finance.payment.dao;

import lu.dave.finance.payment.entity.CustomerEntity;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CustomerRepository extends PagingAndSortingRepository<CustomerEntity, Long>
        , ListCrudRepository<CustomerEntity, Long> {

    boolean existsCustomerEntityByEmail(String email);

}