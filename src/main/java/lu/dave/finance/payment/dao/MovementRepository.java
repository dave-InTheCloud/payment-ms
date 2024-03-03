package lu.dave.finance.payment.dao;

import lu.dave.finance.payment.entity.CustomerEntity;
import lu.dave.finance.payment.entity.MovementEntity;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Repository
public interface MovementRepository extends PagingAndSortingRepository<MovementEntity, Long>
        , ListCrudRepository<MovementEntity, Long> {

    //boolean existsCustomerEntityByEmail(String email);

}