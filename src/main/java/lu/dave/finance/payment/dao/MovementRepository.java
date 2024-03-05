package lu.dave.finance.payment.dao;

import lu.dave.finance.payment.entity.MovementEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@Repository
public interface MovementRepository extends PagingAndSortingRepository<MovementEntity, Long>
        , ListCrudRepository<MovementEntity, Long> {

    // Credit and Debit
    List<MovementEntity> findByAccountIdOrToAccountId(Long id, Long toId);

    //Credit
    Page<MovementEntity> findByAccountId(Long id, Pageable pageable);

    List<MovementEntity> findByAccountId(Long id);

    //Debit
    List<MovementEntity> findByToAccountId(Long id);
}