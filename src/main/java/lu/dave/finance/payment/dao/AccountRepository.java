package lu.dave.finance.payment.dao;

import lu.dave.finance.payment.dto.ChildrenDto;
import lu.dave.finance.payment.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends PagingAndSortingRepository<AccountEntity, Long>
        , ListCrudRepository<AccountEntity, Long>, JpaRepository<AccountEntity, Long> {

    // Add additional custom query methods here if needed
    boolean existsAccountEntityBySerialNumber(String name);

/*
    //return only one line with it's own id if not parent
    @Query(value = """
            WITH ancestors(ID, PARENT_ID) AS (
              SELECT ID, PARENT_ID FROM ACCOUNT WHERE ID = :accountId
              UNION ALL
              SELECT a.ID, a.PARENT_ID FROM ACCOUNT a
              INNER JOIN ancestors c ON a.PARENT_ID = c.ID
            )
            SELECT ID FROM ancestors
            """, nativeQuery = true)
    List<Long> findParentIds(@Param("accountId") Long accountId);

    @Query(value = """
            WITH children(ID, PARENT_ID) AS (
              SELECT ID, PARENT_ID FROM ACCOUNT WHERE PARENT_ID = :parentId
              UNION ALL
                        
              SELECT a.ID, a.PARENT_ID
              FROM ACCOUNT a
              INNER JOIN children c ON a.PARENT_ID = c.ID
            )
            SELECT * FROM children
            """, nativeQuery = true)
    List<Long> findChildren(@Param("parentId") Long parentId);*/
}