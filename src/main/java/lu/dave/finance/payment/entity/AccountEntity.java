package lu.dave.finance.payment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lu.dave.finance.payment.entity.enumaration.AccountType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "ACCOUNT")
public class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACCOUNT_SEQ_GEN")
    @SequenceGenerator(name = "ACCOUNT_SEQ_GEN", sequenceName = "ACCOUNT_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "SERIAL_NUMBER")
    private String serialNumber;

    @Column(name = "CURRENCY_CODE")
    private String currencyCode;

    @Column(name = "BALANCE")
    private Double balance;

    @Column(name = "ACCOUNT_TYPE")
    @Enumerated(EnumType.STRING)
    private AccountType type;

    @ManyToOne
    @JoinColumn(name = "OWNER_ID")
    private CustomerEntity customer;

    @CreationTimestamp
    @Column(name = "CREATED_ON")
    private LocalDateTime createdOn;

    @UpdateTimestamp
    @Column(name = "LAST_UPDATED_ON")
    private LocalDateTime lastUpdatedOn;
}
