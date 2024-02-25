package lu.dave.finance.payment.entity;

import jakarta.persistence.*;
import lombok.Data;
import lu.dave.finance.payment.entity.enumaration.AmountType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "ACCOUNT")
public class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "accountSeq")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private CustomerEntity user;

    @Enumerated(EnumType.STRING)
    private AmountType balanceType;

    @Column(name = "CURRENCY_CODE")
    private String currencyCode;

    @Column(name = "BALANCE")
    private Double balance;

    @CreationTimestamp
    @Column(name = "CREATED_ON")
    private LocalDateTime createdOn;

    @UpdateTimestamp
    @Column(name = "LAST_UPDATED_ON")
    private LocalDateTime lastUpdatedOn;
}
