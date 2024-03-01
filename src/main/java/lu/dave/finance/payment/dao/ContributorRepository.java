package lu.dave.finance.payment.dao;

import lu.dave.finance.payment.entity.ContributorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContributorRepository extends PagingAndSortingRepository<ContributorEntity, Long>
        , ListCrudRepository<ContributorEntity, Long>, JpaRepository<ContributorEntity, Long> {

    // Add additional custom query methods here if needed

}