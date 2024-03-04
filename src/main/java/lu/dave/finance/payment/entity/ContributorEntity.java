/*
package lu.dave.finance.payment.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lu.dave.finance.payment.entity.enumaration.ContributorType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "ACCOUNT_CONTRIBUTOR")
public class ContributorEntity {

    @EmbeddedId
    private ContributorId contributorId;
    @ManyToOne
    @MapsId("accountId")
    @JoinColumn(name = "account_id")
    @JsonIgnore
    private AccountEntity account;

    @ManyToOne
    @MapsId("customerId")
    @JoinColumn(name = "customer_id")
    @JsonIgnore
    private CustomerEntity contributor;

    @Column(name = "AMOUNT")
    private Double amount;

    @Column(name = "TYPE")
    @Enumerated(EnumType.STRING)
    private ContributorType type;

    @Column(name = "CREATED_ON")
    @CreationTimestamp
    private LocalDateTime createdOn;

    @Column(name = "LAST_UPDATED_ON")
    @UpdateTimestamp
    private LocalDateTime lastUpdatedOn;
}
*/
