package lu.dave.finance.payment.entity;

import jakarta.persistence.*;
import lombok.Data;
import lu.dave.finance.payment.entity.enumaration.AmountType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "MOVEMENT")
public class MovementEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MOVEMENT_SEQ_GEN")
    @SequenceGenerator(name = "MOVEMENT_SEQ_GEN", sequenceName = "MOVEMENT_SEQ", allocationSize = 1)
    private Long id;

    private Long customerId;
    @ManyToOne
    @JoinColumn(name = "from_account_id")
    private AccountEntity fromAccount;

    @ManyToOne
    @JoinColumn(name = "to_account_id")
    private AccountEntity toAccount;

    private Double amount;

    @Enumerated(EnumType.STRING)
    private AmountType amountType;

    @CreationTimestamp
    @Column(name = "CREATED_ON")
    private LocalDateTime createdOn;

    @UpdateTimestamp
    @Column(name = "LAST_UPDATED_ON")
    private LocalDateTime lastUpdatedOn;
}
