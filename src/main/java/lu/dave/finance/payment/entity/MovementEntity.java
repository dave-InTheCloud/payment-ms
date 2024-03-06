package lu.dave.finance.payment.entity;

import jakarta.persistence.*;
import lombok.Data;
import lu.dave.finance.payment.entity.enumaration.MovementStatus;
import lu.dave.finance.payment.entity.enumaration.MovementType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "MOVEMENT")
public class MovementEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MOVEMENT_SEQ_GEN")
    @SequenceGenerator(name = "MOVEMENT_SEQ_GEN", sequenceName = "MOVEMENT_SEQ", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ACCOUNT_ID")
    private AccountEntity account;

    @ManyToOne
    @JoinColumn(name = "TO_ACCOUNT_ID")
    private AccountEntity toAccount;

    @Column(name = "CURRENCY_CODE")
    private String currencyCode;

    private Double amount;

    @Enumerated(EnumType.STRING)
    private MovementType movementType;

    @Enumerated(EnumType.STRING)
    private MovementStatus status;

    @Column(name = "ORDERED_ON")
    private LocalDateTime orderedOn;

    @CreationTimestamp
    @Column(name = "CREATED_ON")
    private LocalDateTime createdOn;

    @UpdateTimestamp
    @Column(name = "LAST_UPDATED_ON")
    private LocalDateTime lastUpdatedOn;
}
